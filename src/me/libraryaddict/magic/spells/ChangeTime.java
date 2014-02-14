package me.libraryaddict.magic.spells;

import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class ChangeTime extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 0) {
            try {
                int time = Integer.parseInt(args[0]);
                player.getWorld().setTime(time);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
