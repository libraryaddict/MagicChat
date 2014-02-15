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
        Skript.registerExpression(ExprSpellArgs.class, String.class, ExpressionType.SIMPLE, "args", "spell args");
    }
}
