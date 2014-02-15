package me.libraryaddict.magic.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ChatSpell {
    private boolean isCaseSensitive;
    private String permission;
    private String[] playerChat;
    private String[] pluginChat;
    private String spellName;
    private HashMap<Integer, HashMap<String, int[]>> spellsToRun = new HashMap<Integer, HashMap<String, int[]>>();

    public ChatSpell(String spellName, String permission, boolean isCaseSensitive, String[] playerChat, String[] pluginChat) {
        this.spellName = spellName;
        this.permission = (permission == null ? null : permission.toLowerCase());
        this.isCaseSensitive = isCaseSensitive;
        this.playerChat = playerChat;
        this.pluginChat = pluginChat;
    }

    public String[] getArgs(String playerMessage, String origMessage) {
        ArrayList<String> args = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        char[] chars = playerMessage.toCharArray();
        String arg = "";
        boolean b = false;
        if (!isCaseSensitive()) {
            origMessage = origMessage.toLowerCase();
        }
        for (int i = 0; i < chars.length; i++) {
            char c = isCaseSensitive() ? chars[i] : Character.toLowerCase(chars[i]);
            if (origMessage.startsWith(builder.toString() + c)) {
                builder.append(c);
                if (arg.length() > 0) {
                    b = false;
                    args.add(arg);
                    arg = "";
                }
            } else {
                arg += chars[i];
                if (!b) {
                    b = true;
                    builder.append("%arg%");
                }
            }
        }
        if (arg.length() > 0) {
            args.add(arg);
        }
        if (builder.toString().equals(origMessage)) {
            return args.toArray(new String[args.size()]);
        }
        return null;
    }

    /**
     * Returns the number of lines the player chat matches..
     */
    public int getMatches(ArrayList<String> playerChat) {
        String[] lines = getPlayerChat();
        /**
         * Stores the number the first match was found, and how many matches after that. Removes the number if the matches ends
         * before the player chat ends
         */
        HashMap<Integer, Integer> matches = new HashMap<Integer, Integer>();
        for (int i = 0; i < playerChat.size(); i++) {
            String chat = playerChat.get(i);
            Iterator<Integer> itel = matches.keySet().iterator();
            while (itel.hasNext()) {
                int started = itel.next();
                if (lines.length > matches.get(started) && matches(chat, lines[matches.get(started)])) {
                    matches.put(started, matches.get(started) + 1);
                } else {
                    itel.remove();
                }
            }
            if (matches(chat, lines[0])) {
                matches.put(i, 1);
            }
        }
        int highest = 0;
        for (int match : matches.keySet()) {
            if (matches.get(match) > highest) {
                highest = matches.get(match);
            }
        }
        return highest;
    }

    public String getName() {
        return spellName;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getPlayerChat() {
        return playerChat;
    }

    public String[] getPluginChat() {
        return pluginChat;
    }

    public HashMap<String, int[]> getSpellsToRun(int key) {
        if (!spellsToRun.containsKey(key)) {
            return new HashMap<String, int[]>();
        }
        return spellsToRun.get(key);
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public boolean isReplaceChat() {
        return pluginChat != null;
    }

    private boolean matches(String playerMessage, String originalLine) {
        String[] args = getArgs(playerMessage, originalLine);
        return args != null;
    }

    public void registerSpell(int key, String spell, int[] argsToPass) {
        HashMap<String, int[]> spells = getSpellsToRun(key);
        spells.put(spell, argsToPass);
        spellsToRun.put(key, spells);
    }
}
