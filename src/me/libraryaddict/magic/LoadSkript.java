package me.libraryaddict.magic;

import me.libraryaddict.magic.types.ExprSpellArgs;
import me.libraryaddict.magic.types.ExprSpellName;
import me.libraryaddict.magic.types.SpellCast;
import me.libraryaddict.magic.types.SpellCastEvent;

import org.bukkit.entity.Player;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.SerializableGetter;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.EventValues;

public class LoadSkript {
    @SuppressWarnings("serial")
    public static void loadSkript() {
        Skript.registerEvent("Spell Cast", SpellCast.class, SpellCastEvent.class, "spell cast", "magic spell")
                .description(new String[] { "Called when a spell is cast." }).examples(new String[] { "spell cast" });
        EventValues.registerEventValue(SpellCastEvent.class, Player.class, new SerializableGetter<Player, SpellCastEvent>() {
            @Override
            public Player get(SpellCastEvent e) {
                return e.getPlayer();
            }
        }, 0);
        Skript.registerExpression(ExprSpellName.class, String.class, ExpressionType.SIMPLE, "spell", "spell name");
        Skript.registerExpression(ExprSpellArgs.class, String.class, ExpressionType.SIMPLE, "[the] last spell arg[ument][s]",
                "[the] spell arg[ument][s](-| )<(\\d+)>", "[the] <(\\d*1)st|(\\d*2)nd|(\\d*3)rd|(\\d*[4-90])th> spell arg[ument][s]",
                "[the] spell arg[ument][s]", "[the] %*classinfo%( |-)spell arg[ument][( |-)<\\d+>]",
                "[the] spell arg[ument]( |-)%*classinfo%[( |-)<\\d+>]");
    }
}
