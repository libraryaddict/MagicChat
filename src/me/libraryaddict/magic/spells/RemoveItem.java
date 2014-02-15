package me.libraryaddict.magic.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.libraryaddict.magic.types.Spell;

public class RemoveItem extends Spell {

    @Override
    public void invokeSpell(Player player, String[] args) {
        if (args.length > 1) {
            args[0] = args[0].replaceAll("[^a-zA-Z0-9_\\s]", "");
            Material mat = null;
            try {
                mat = Material.valueOf(args[1].toUpperCase());
            } catch (Exception ex) {
                try {
                    mat = Material.getMaterial(Integer.parseInt(args[1]));
                } catch (Exception e) {

                }
            }
            if (mat != null) {
                String[] names = args[0].split(" ");
                for (String name : names) {
                    Player p = Bukkit.getPlayer(name);
                    if (p != null) {
                        removeItem(p, mat);
                    } else if (name.equalsIgnoreCase("everyone")) {
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            removeItem(pl, mat);
                        }
                    }
                }
            }
        }
    }

    private void removeItem(Player player, Material mat) {
        player.getInventory().remove(mat);
        if (player.getItemOnCursor() != null && player.getItemOnCursor().getType() == mat) {
            player.setItemOnCursor(new ItemStack(Material.AIR));
        }
        ItemStack[] items = player.getInventory().getArmorContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == mat) {
                items[i] = new ItemStack(Material.AIR);
            }
        }
        player.getInventory().setArmorContents(items);
    }
}
