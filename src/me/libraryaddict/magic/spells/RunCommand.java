package me.libraryaddict.magic.spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class RunCommand extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 1) {
            args[0] = args[0].replaceAll("[^A-Za-z0-9][^a-zA-Z0-9\\s]", "");
            String[] names = args[0].split(" ");
            if (args[1].startsWith("/")) {
                args[1] = args[1].substring(1);
            }
            for (String name : names) {
                Player p = Bukkit.getPlayer(name);
                if (p != null) {
                    Bukkit.dispatchCommand(p, args[1].replace("%me%", p.getName()));
                } else if (name.equalsIgnoreCase("everyone")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (player != pl)
                            Bukkit.dispatchCommand(pl, args[1].replace("%me%", pl.getName()));
                    }
                }
            }
        }
    }
}
