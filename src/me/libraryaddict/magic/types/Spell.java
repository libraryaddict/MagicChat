package me.libraryaddict.magic.types;

import org.bukkit.entity.Player;

public abstract class Spell {
    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract void invokeSpell(Player player, String[] args);
}
