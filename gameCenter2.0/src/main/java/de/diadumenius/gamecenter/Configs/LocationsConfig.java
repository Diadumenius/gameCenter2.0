package de.diadumenius.gamecenter.Configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

// this class was created by Diadumenius
public class LocationsConfig {

    private static File file;
    private static YamlConfiguration config;

    public LocationsConfig() {

        File dir = new File("./plugins/gamecenter/LocationsConfig");
        if(!dir.exists()){
            dir.mkdirs();
        }

        file = new File(dir,"LocationsConfig.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

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

    public static void setLocation(String path, Location loc){
        String x = String.valueOf(loc.getBlockX());
        String y = String.valueOf(loc.getBlockY());
        String z = String.valueOf(loc.getBlockZ());
        try {
            set(path + ".World", loc.getWorld().getName());
            set(path + ".X", Double.parseDouble(x));
            set(path + ".Y", Double.parseDouble(y));
            set(path + ".Z", Double.parseDouble(z));
            config.save(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerLocation(String path, Location loc){
        try {
            set(path + ".World", loc.getWorld().getName());
            set(path + ".X", loc.getX());
            set(path + ".Y", loc.getY());
            set(path + ".Z", loc.getZ());

            set(path + ".Yaw", loc.getYaw());
            set(path + ".Pitch", loc.getPitch());
            config.save(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Location getLocation(String path){
        Location loc;

        if(contains(path)){
            World world = (Bukkit.getWorld((String) get(path + ".World")));
            Double x = (Double) get(path + ".X");
            Double y = (Double) get(path + ".Y");
            Double z = (Double) get(path + ".Z");
            loc = new Location(world,x,y,z);

            if(contains(path + "Yaw")){
                Float yaw = (Float) get(path + ".Yaw");
                Float pitch = (Float) get(path + ".Pitch");
                loc = new Location(world,x,y,z, yaw,pitch);
            }
            return loc;
        }else
            return null;
    }
}
