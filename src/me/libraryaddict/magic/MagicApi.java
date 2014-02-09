package me.libraryaddict.magic;

import java.util.ArrayList;

import me.libraryaddict.magic.types.ChatSpell;
import me.libraryaddict.magic.types.Spell;

public class MagicApi {
    private static ArrayList<ChatSpell> chatSpells = new ArrayList<ChatSpell>();
    private static ArrayList<Spell> spells = new ArrayList<Spell>();

    public static ArrayList<ChatSpell> getChatSpells() {
        return chatSpells;
    }

    public static Spell getSpell(String spellName) {
        for (Spell spell : spells) {
            if (spell.getName().equals(spellName)) {
                return spell;
            }
        }
        return null;
    }

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public static void registerChatSpell(ChatSpell chatSpell) {
        for (ChatSpell spell : chatSpells) {
            if (spell.getName().equals(chatSpell.getName())) {
                throw new RuntimeException("[MagicChat] The chat spell " + spell.getName() + " has already been registered!");
            }
        }
        System.out.println("[MagicChat] Registered chat spell " + chatSpell.getName());
        chatSpells.add(chatSpell);
    }

    public static void registerSpell(Spell spell) {
        if (getSpell(spell.getName()) == null) {
            spells.add(spell);
            System.out.println("[MagicChat] Registered magic spell " + spell.getName());
        } else {
            throw new RuntimeException("[MagicChat] The magic spell " + spell.getName() + " has already been registered!");
        }
    }
}
