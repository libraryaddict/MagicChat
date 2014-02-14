package me.libraryaddict.magic.spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class GiveEveryone extends Spell {

    @Override
    public void invokeSpell(Player p, String[] args) {
        if (args.length > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " " + args[0]);
            }
        }
    }

}
