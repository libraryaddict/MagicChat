package me.libraryaddict.magic.spells;

import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class ChangeTime extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 0) {
            try {
                String s = args[0].toLowerCase();
                int time = -1;
                if (s.replaceAll("\\D+", "").length() > 0) {
                    time = Integer.parseInt(s.replaceAll("\\D+", ""));
                    if (time != 0) {
                        while (time < 1000) {
                            time *= 10;
                        }
                    }
                }
                if (s.equals("night") || s.equals("midnight")) {
                    time = 13000;
                } else if (s.equals("day")) {
                    time = 0;
                } else if (s.contains("am") || s.contains("pm")) {
                    time += 7000;
                    if (s.contains("pm")) {
                        time += 12000;
                    } else if (s.contains("am")) {
                        time -= 12000;
                    }
                } else if (time != -1) {
                    return;
                }
                player.getWorld().setTime(time);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
