package me.libraryaddict.magic.spells;

import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class DarkenNight extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        player.getWorld().setTime(13000);
    }

}
