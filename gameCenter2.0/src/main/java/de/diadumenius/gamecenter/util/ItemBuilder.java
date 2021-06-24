package de.diadumenius.gamecenter.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

// this class was created by Diadumenius
public class ItemBuilder {

    private final String displayName;
    private final ArrayList<String> lore;
    private final Boolean unbreakable;
    private final Material material;

    public ItemBuilder(String displayName, ArrayList<String> lore, Boolean unbreakable, Material material) {
        this.displayName = displayName;
        this.lore = lore;
        this.unbreakable = unbreakable;
        this.material = material;
    }

    public ItemStack create(){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }
}
