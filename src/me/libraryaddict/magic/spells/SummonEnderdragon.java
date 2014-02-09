package me.libraryaddict.magic.spells;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.types.Spell;

public class SummonEnderdragon extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        player.getWorld().playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 0, 1000);
        player.getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_DRAGON);
    }

}
