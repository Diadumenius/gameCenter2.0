package de.diadumenius.gamecenter.functions;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

// this class was created by Diadumenius
public class InventoryFunctions {

    public static Inventory fillWith(Material material, Inventory inv){
        ItemStack item = new ItemStack(material);
        for(int i = 0; i <= inv.getSize() - 1; i++){
            inv.setItem(i, item);
        }
        return inv;
    }
}
