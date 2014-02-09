package me.libraryaddict.magic.types;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SpellCastEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String[] args;
    private String spell;

    public SpellCastEvent(Player player, String spellName, String[] args) {
        super(player);
        this.spell = spellName;
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public String getSpell() {
        return spell;
    }
}
