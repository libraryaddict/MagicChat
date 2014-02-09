package me.libraryaddict.magic.spells;

import me.libraryaddict.magic.types.Spell;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem extends Spell {

    @Override
    public void invokeSpell(Player p, String[] args) {
        try {
            Material mat = Material.getMaterial(args[0].toUpperCase());
            p.getInventory().addItem(new ItemStack(mat, mat.getMaxStackSize()));
        } catch (Exception ex) {

        }
    }

}
