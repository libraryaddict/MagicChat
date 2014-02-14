package me.libraryaddict.magic.spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ch.njol.util.StringUtils;
import me.libraryaddict.magic.types.Spell;

public class Ban extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 0) {
            Bukkit.dispatchCommand(player, "ban " + StringUtils.join(args, " "));
        }
    }

}
