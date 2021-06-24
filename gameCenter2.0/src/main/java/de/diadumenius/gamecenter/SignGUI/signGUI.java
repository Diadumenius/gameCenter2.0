package de.diadumenius.gamecenter.SignGUI;

import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenSignEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class signGUI{

    private static String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);

    public static void openAgeSign(Player player){
        if(LocationsConfig.contains("Locations.Signs.AgeSign")) {
            Location loc = LocationsConfig.getLocation("Locations.Signs.AgeSign");
            assert loc != null;
            loc.getChunk().load();
            signGUI.openSign(player, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), " ");
        }else{
            player.sendMessage(ERROR_PREFIX + "§cKeine Sign-Position gespeichert!");
        }
    }

    public static void openDescriptionSign(Player player){
        if(LocationsConfig.contains("Locations.Signs.DescriptionSign")) {
            Location loc = LocationsConfig.getLocation("Locations.Signs.DescriptionSign");
            assert loc != null;
            loc.getChunk().load();
            signGUI.openSign(player, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), " ");
        }else{
            player.sendMessage(ERROR_PREFIX + "§cKeine Sign-Position gespeichert!");
        }
    }

    public static void openSign(Player player, int x, int y, int z, String title) {
        try {

            Location loc = new Location(player.getWorld(), Double.parseDouble(String.valueOf(x)),Double.parseDouble(String.valueOf(y)),Double.parseDouble(String.valueOf(z)));

            loc.getBlock().setType(Material.ACACIA_SIGN);

            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);

            Class<?> packetClass = getNMSClass("PacketPlayOutOpenSignEditor");
            Class<?> blockPositionClass = getNMSClass("BlockPosition");
            assert blockPositionClass != null;
            Constructor<?> blockPosCon = blockPositionClass.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);

            BlockPosition blockPosition = new BlockPosition(x,y,z);
            assert packetClass != null;
            Constructor<?> packetCon = packetClass.getConstructor(blockPositionClass);

            Sign sign = (Sign) player.getWorld().getBlockAt(x, y, z).getState();
            Field tileEntityField = sign.getClass().getSuperclass().getDeclaredField("tileEntity");
            tileEntityField.setAccessible(true);

            Object tileEntitySign = tileEntityField.get(sign);

            Field signIsEditable = tileEntitySign.getClass().getDeclaredField("isEditable");
            signIsEditable.setAccessible(true);
            // Ensure TileEntitySign is editable
            signIsEditable.set(tileEntitySign, true);

            Field signEntityHumanField = tileEntitySign.getClass().getDeclaredField("c");
            signEntityHumanField.setAccessible(true);
            // Designate the EntityPlayer as the editor (EntityHuman) of the TileEntitySign
            signEntityHumanField.set(tileEntitySign, entityPlayer);

            sign.setLine(0, title);
            sign.setLine(1, null);
            sign.setLine(2, null);
            sign.setLine(3, "---------------");
            if(!sign.isEditable()) {
                sign.setEditable(true);
            }
            sign.update();

            Object packet = packetCon.newInstance(blockPosition);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(blockPosition));



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void openGui(Player p, Sign s) {
        try {
            Field field = s.getClass().getDeclaredField("h"); //Get the "TileEntitySign sign" field
            field.setAccessible(true); //Make it accessible
            Object tileEntity = field.get(s); //Get the TileEntitySign from the field.

            Field editable = tileEntity.getClass().getDeclaredField("isEditable"); //Get the field "editable" from TileEntitySign
            editable.set(tileEntity, true); //Set it to true

            Field owner = tileEntity.getClass().getDeclaredField("k"); //Get the field "k" from TileEntitySign
            owner.setAccessible(true); //Make it accessible
            owner.set(tileEntity, ((CraftPlayer) p).getHandle()); //Set it to the craftplayer

            BlockPosition blockPosition = new BlockPosition(s.getX(),s.getY(),s.getZ());

            PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(blockPosition);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server."
                    + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
