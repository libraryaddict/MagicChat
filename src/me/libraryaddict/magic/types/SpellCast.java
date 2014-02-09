package me.libraryaddict.magic.types;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

@SuppressWarnings("serial")
public class SpellCast extends SkriptEvent {

    public boolean check(Event e) {
        return e instanceof SpellCastEvent;
    }

    public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parser) {
        return true;
    }

    public String toString(Event e, boolean debug) {
        return "spell cast";
    }
}
