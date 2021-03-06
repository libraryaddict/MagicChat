package me.libraryaddict.magic.types;

import org.bukkit.event.Event;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@SuppressWarnings("serial")
public class ExprSpellName extends SimpleExpression<String> {

    @Override
    protected String[] get(Event e) {
        if (!(e instanceof SpellCastEvent))
            return new String[0];
        return new String[] { ((SpellCastEvent) e).getSpell() };
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(SpellCastEvent.class)) {
            Skript.error("The expression 'spell name' can only be used in spell cast events", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "spell name";
    }
}
