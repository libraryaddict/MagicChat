package me.libraryaddict.magic.spells;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class WolfHowl extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        player.getWorld().playSound(player.getLocation(), Sound.WOLF_HOWL, 5, 1000);
    }

}
