package me.libraryaddict.magic.spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class RunCommandAsOp extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 1) {
            args[0] = args[0].replaceAll("[^a-zA-Z0-9_\\s]", "");
            String[] names = args[0].split(" ");
            if (args[1].startsWith("/")) {
                args[1] = args[1].substring(1);
            }
            for (String name : names) {
                Player p = Bukkit.getPlayer(name);
                if (p != null) {
                    boolean op = p.isOp();
                    if (!op) {
                        p.setOp(true);
                    }
                    Bukkit.dispatchCommand(p, args[1].replace("%me%", p.getName()));
                    if (!op) {
                        p.setOp(false);
                    }
                } else if (name.equalsIgnoreCase("everyone")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (player == pl)
                            continue;
                        boolean op = pl.isOp();
                        if (!op) {
                            pl.setOp(true);
                        }
                        Bukkit.dispatchCommand(pl, args[1].replace("%me%", pl.getName()));
                        if (!op) {
                            pl.setOp(false);
                        }
                    }
                }
            }
        }
    }
}
