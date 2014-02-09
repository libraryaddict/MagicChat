package me.libraryaddict.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.libraryaddict.magic.types.ChatSpell;
import me.libraryaddict.magic.types.ClassGetter;
import me.libraryaddict.magic.types.ExprSpellArgs;
import me.libraryaddict.magic.types.ExprSpellName;
import me.libraryaddict.magic.types.Spell;
import me.libraryaddict.magic.types.SpellCast;
import me.libraryaddict.magic.types.SpellCastEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.SerializableGetter;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.EventValues;

public class MagicChat extends JavaPlugin implements Listener {

    private HashMap<String, ArrayList<String>> playerChat = new HashMap<String, ArrayList<String>>();

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        ArrayList<String> chat = new ArrayList<String>();
        Player p = event.getPlayer();
        if (playerChat.containsKey(p.getName())) {
            chat = playerChat.get(p.getName());
        } else {
            playerChat.put(p.getName(), chat);
        }
        chat.add(ChatColor.stripColor(event.getMessage()));
        boolean hasMatch = false;
        for (ChatSpell spell : MagicApi.getChatSpells()) {
            if (spell.getPermission() == null || p.hasPermission(spell.getPermission())) {
                int spellLine = spell.getMatches(chat);
                if (spellLine > 0) {
                    hasMatch = true;
                    String[] args = spell.getArgs(ChatColor.stripColor(event.getMessage()), spell.getPlayerChat()[spellLine - 1]);
                    HashMap<String, int[]> spells = spell.getSpellsToRun(spellLine);
                    for (String spellName : spells.keySet()) {
                        int[] toPass = spells.get(spellName);
                        String[] newArgs;
                        if (toPass.length == 0) {
                            newArgs = new String[0];
                        } else {
                            newArgs = new String[toPass.length];
                            for (int i = 0; i < toPass.length; i++) {
                                int pass = toPass[i];
                                if (args.length > pass) {
                                    newArgs[i] = args[pass];
                                }
                            }
                        }
                        Spell rSpell = MagicApi.getSpell(spellName);
                        if (rSpell != null) {
                            rSpell.invokeSpell(p, newArgs);
                        }
                        Bukkit.getPluginManager().callEvent(new SpellCastEvent(p, spellName, newArgs));
                    }
                    if (spell.isReplaceChat()) {
                        String rp = spell.getPluginChat()[spellLine - 1];
                        for (int i = 0; i < args.length; i++) {
                            rp = rp.replaceFirst("%arg%", args[i]);
                        }
                        event.setMessage(rp);
                    }
                }
            }
        }
        if (!hasMatch) {
            playerChat.remove(p.getName());
        } else if (event.getMessage().length() == 0) {
            event.setCancelled(true);
        }
    }

    @SuppressWarnings("serial")
    public void onEnable() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Skript");
        if (plugin != null) {
            Skript.registerEvent("Spell Cast", SpellCast.class, SpellCastEvent.class, "spell cast", "magic spell")
                    .description(new String[] { "Called when a spell is cast." }).examples(new String[] { "spell cast" });
            EventValues.registerEventValue(SpellCastEvent.class, Player.class, new SerializableGetter<Player, SpellCastEvent>() {
                @Override
                public Player get(SpellCastEvent e) {
                    return e.getPlayer();
                }
            }, 0);
            EventValues.registerEventValue(SpellCastEvent.class, String.class, new SerializableGetter<String, SpellCastEvent>() {
                @Override
                public String get(SpellCastEvent e) {
                    return e.getSpell();
                }
            }, 0);
            EventValues.registerEventValue(SpellCastEvent.class, String[].class,
                    new SerializableGetter<String[], SpellCastEvent>() {
                        @Override
                        public String[] get(SpellCastEvent e) {
                            return e.getArgs();
                        }
                    }, 0);
            Skript.registerExpression(ExprSpellName.class, String.class, ExpressionType.SIMPLE, "spell", "spell name");
            Skript.registerExpression(ExprSpellArgs.class, String.class, ExpressionType.SIMPLE, "args", "spell args");
        }
        for (Class c : ClassGetter.getClassesForPackage(this, "me.libraryaddict.magic.spells")) {
            if (Spell.class.isAssignableFrom(c)) {
                try {
                    Spell spell = (Spell) c.newInstance();
                    MagicApi.registerSpell(spell);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("The class " + c.getSimpleName() + " was found in the spells package. Why?");
            }
        }
        saveDefaultConfig();
        for (String spellName : getConfig().getConfigurationSection("Spells").getKeys(false)) {
            ConfigurationSection section = getConfig().getConfigurationSection("Spells." + spellName);
            List<String> stringList = section.getStringList("PlayerChat");
            String[] playerChat = stringList.toArray(new String[stringList.size()]);
            String[] pluginChat = null;
            if (section.contains("PluginChat")) {
                stringList = section.getStringList("PluginChat");
                if (stringList.size() != playerChat.length) {
                    System.out
                            .println("[MagicSpells] Error! The length of pluginchat does not match player chat! Skipping spell "
                                    + spellName + "!");
                    continue;
                }
                pluginChat = stringList.toArray(new String[stringList.size()]);
                for (int i = 0; i < pluginChat.length; i++) {
                    pluginChat[i] = ChatColor.translateAlternateColorCodes('&', pluginChat[i]);
                }
            }
            ChatSpell chatSpell = new ChatSpell(spellName, section.getString("Permission"), section.getBoolean("CaseSensitive",
                    false), playerChat, pluginChat);
            for (String info : section.getStringList("Spells")) {
                String[] split = info.split(" ");
                int key = Integer.parseInt(split[0]);
                String magicSpell = split[1];
                int[] argsToPassToSpell = new int[split.length - 2];
                for (int i = 2; i < split.length; i++) {
                    argsToPassToSpell[i - 2] = Integer.parseInt(split[i]) - 1;
                }
                chatSpell.registerSpell(key, magicSpell, argsToPassToSpell);
            }
            MagicApi.registerChatSpell(chatSpell);
        }
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerChat.remove(event.getPlayer().getName());
    }
}
