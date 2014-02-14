package me.libraryaddict.magic.spells;

import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class ChangeTime extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 0) {
            try {
                String s = args[0];
                s = s.replaceAll("\\D+", "");
                int time = Integer.parseInt(s);
                if (time != 0) {
                    while (time < 1000) {
                        time *= 10;
                    }
                }
                if (args[0].toLowerCase().contains("pm")) {
                    time += 12000;
                }
                player.getWorld().setTime(time);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
