package me.libraryaddict.magic.spells;

import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class SetAllowFly extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        player.setAllowFlight(true);
        player.setFlying(true);
    }

}
