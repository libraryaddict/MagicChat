package me.libraryaddict.magic.spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class Strike extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 0) {
            args[0] = args[0].replaceAll("[^a-zA-Z0-9_\\s]", "");
            String[] names = args[0].split(" ");
            for (String name : names) {
                Player p = Bukkit.getPlayer(name);
                if (p != null) {
                    p.getWorld().strikeLightning(p.getLocation());
                }
            }
        }
    }

}
