package de.diadumenius.gamecenter.Configs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

// this class was created by Diadumenius
public class InventoryConfig {

    private static File file;
    public static YamlConfiguration config;

    public InventoryConfig() {

        File dir = new File("./plugins/gamecenter/InventoryConfig");
        if(!dir.exists()){
            dir.mkdirs();
        }

        file = new File(dir,"config.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public static void saveInventoryContents(String path, Inventory inv) {
        try {
            set(path, inv.getContents());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemStack[] getInventoryContents(String path){
        ItemStack[] contents = null;
        if(contains(path)){
            contents = (ItemStack[]) get(path);
        }
        return contents;
    }

    public static Boolean contains(String path){
        return config.contains(path);
    }

    public static void set(String path, Object value) throws IOException {
        config.set(path, value);
        config.save(file);
    }

    public static Object get(String path){
        if(!contains(path)){
            return null;
        }
        return config.get(path);
    }

}
