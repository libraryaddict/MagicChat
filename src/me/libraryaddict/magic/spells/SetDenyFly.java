package me.libraryaddict.magic.spells;

import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class SetDenyFly extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        player.setFallDistance(0);
        player.setAllowFlight(false);
    }

}
