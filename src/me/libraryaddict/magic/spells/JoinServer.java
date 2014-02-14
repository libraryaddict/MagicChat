package me.libraryaddict.magic.spells;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.libraryaddict.magic.MagicApi;
import me.libraryaddict.magic.types.Spell;

public class JoinServer extends Spell {
    private void joinServer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server); // Target Server
        } catch (IOException e) {
        }
        player.sendPluginMessage(MagicApi.getMainPlugin(), "BungeeCord", b.toByteArray());
    }

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 1) {
            args[0] = args[0].replaceAll("[^A-Za-z0-9][^a-zA-Z0-9\\s]", "");
            String[] names = args[0].split(" ");
            for (String name : names) {
                Player p = Bukkit.getPlayer(name);
                if (p != null) {
                    joinServer(p, args[1]);
                } else if (name.equalsIgnoreCase("everyone")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (player != pl)
                            joinServer(pl, args[1]);
                    }
                }
            }
        }
    }
}
