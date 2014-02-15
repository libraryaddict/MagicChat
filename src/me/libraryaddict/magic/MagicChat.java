package me.libraryaddict.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.libraryaddict.magic.types.ChatSpell;
import me.libraryaddict.magic.types.ClassGetter;
import me.libraryaddict.magic.types.Configurable;
import me.libraryaddict.magic.types.Spell;
import me.libraryaddict.magic.types.SpellCastEvent;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;

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

public class MagicChat extends JavaPlugin implements Listener {

    private HashMap<String, ArrayList<String>> playerChat = new HashMap<String, ArrayList<String>>();

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        ArrayList<String> chat = new ArrayList<String>();
        final Player p = event.getPlayer();
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
                    String[] args = new String[0];
                    int chatSize = chat.size() - spellLine;
                    for (int i = 0; i < spellLine; i++) {
                        String msg = chat.get(chatSize + i);
                        String[] newArgs = spell.getArgs(msg, spell.getPlayerChat()[i]);
                        args = ArrayUtils.addAll(args, newArgs);
                    }
                    HashMap<String, int[]> spells = spell.getSpellsToRun(spellLine);
                    for (final String spellName : spells.keySet()) {
                        int[] toPass = spells.get(spellName);
                        final String[] newArgs;
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
                        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            public void run() {
                                Spell rSpell = MagicApi.getSpell(spellName);
                                if (rSpell != null) {
                                    rSpell.invokeSpell(p, newArgs);
                                }
                                Bukkit.getPluginManager().callEvent(new SpellCastEvent(p, spellName, newArgs));
                            }
                        });
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

    public void onEnable() {
        MagicApi.setMainPlugin(this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Plugin skriptPlugin = Bukkit.getPluginManager().getPlugin("Skript");
        if (skriptPlugin != null) {
            LoadSkript.loadSkript();
        }
        for (Class c : ClassGetter.getClassesForPackage(this, "me.libraryaddict.magic.spells")) {
            if (Spell.class.isAssignableFrom(c)) {
                try {
                    Spell spell = (Spell) c.newInstance();
                    if (spell instanceof Configurable) {
                        ((Configurable) spell).loadConfig();
                    }
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
