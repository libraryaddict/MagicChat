package me.libraryaddict.magic.types;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;
import ch.njol.util.StringUtils;

@SuppressWarnings("serial")
public class ExprSpellArgs extends SimpleExpression<String> {

    private int index;

    @Override
    protected String[] get(final Event e) {
        if (!(e instanceof SpellCastEvent))
            return null;
        String[] args = ((SpellCastEvent) e).getArgs();
        if (index != 40 && args.length <= index) {
            return new String[0];
        }
        return new String[] { args[index == 40 ? args.length - 1 : index] };
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parser) {
        switch (matchedPattern) {
        case 0:
            /**
             * No args will go this high.. Right?
             */
            index = 40;
            break;
        case 1:
        case 2:
            index = Utils.parseInt(parser.regexes.get(0).group(1));
            break;
        case 3:
            index = 0;
            break;
        case 4:
        case 5:
            index = parser.regexes.size() > 0 ? Utils.parseInt(parser.regexes.get(0).group()) : -1;
            break;
        }
        return true;
    }

    @Override
    public boolean isLoopOf(final String s) {
        return s.equalsIgnoreCase("argument");
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        if (e == null)
            return "the " + StringUtils.fancyOrderNumber(index) + " argument";
        return Classes.getDebugMessage(getArray(e));
    }

}
