package de.diadumenius.gamecenter.util;

import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.SignGUI.signGUI;
import de.diadumenius.gamecenter.functions.InventoryFunctions;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

// this class was created by Diadumenius
public class LoginProcess implements Listener {

    private static String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);
    private final String GAMECENTER_PREFIX = PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA);

    private Location AgeLocation;
    private Location DescriptionLocation;


    private static String PROFILE_SETTINGS_NAME = "§a§lProfil-Einstellungen";

    public static void openProfileSettings(Gamer gamer, Boolean isFirstTime){
        Inventory inv = Bukkit.createInventory(null, 9*3, PROFILE_SETTINGS_NAME);
        inv = InventoryFunctions.fillWith(Material.GRAY_STAINED_GLASS_PANE, inv);

        ArrayList<String> lore = new ArrayList<>();
        lore.clear();
        lore.add("§8Ändere dein Alter");
        lore.add(" ");
        lore.add("§7§oKlicke, um dein Alter");
        lore.add("§7§oanzugeben. Dieses kann jeder");
        lore.add("§7§osehen. Bitte kein Fake-Alter");
        lore.add("§7§oangeben!");
        lore.add(" ");
        lore.add("§6§nDein jetziges Alter: §6§n" + gamer.getAge());

        inv.setItem(11, new ItemBuilder("§6§lAlter", lore, false, Material.LIME_STAINED_GLASS).create());

        lore.clear();
        lore.add("§8Ändere deine Beschreibung");
        lore.add(" ");
        lore.add("§7§oKlicke, um deine Beschreibung");
        lore.add("§7§oanzugeben. Diese kann jeder");
        lore.add("§7§osehen. Bitte keine Fake-Beschreibung");
        lore.add("§7§oangeben!");
        lore.add(" ");
        lore.add("§6§nDeine jetzige Beschreibung: §6§n" + gamer.getDescription());

        inv.setItem(12, new ItemBuilder("§6§lBeschreibung", lore, false, Material.PURPLE_STAINED_GLASS).create());

        gamer.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        AgeLocation = LocationsConfig.getLocation("Locations.Signs.AgeSign");
        DescriptionLocation = LocationsConfig.getLocation("Locations.Signs.DescriptionSign");
        Sign sign = (Sign) event.getBlock().getState();
        String result = event.getLine(0) + event.getLine(1) + event.getLine(2) + event.getLine(3);
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if(sign.getLocation().add(0,0,0).equals(AgeLocation)){
            int age;
            try {
                age = Integer.parseInt(result);
            } catch(NumberFormatException e)
            {
                player.kickPlayer(ERROR_PREFIX + "§cUngültiges Alter!");
                return;
            }
            if(age > 10){
                if(age < 80){
                    gamer.setAge(age);
                    gamer.saveDataInConfig(player);
                    player.sendMessage(GAMECENTER_PREFIX + "§bDein Alter wurde zu " + ChatColor.GOLD + ChatColor.BOLD + age + " §bgeändert!");
                }else
                    player.kickPlayer(ERROR_PREFIX + "§cDu bist zu alt zum spielen!");
            }else
                player.kickPlayer(ERROR_PREFIX + "§cDu bist noch zu jung!");

        }else if(sign.getLocation().equals(DescriptionLocation)){
            gamer.setDescription(result);
            gamer.saveDataInConfig(player);
            player.sendMessage(GAMECENTER_PREFIX + "§bDeine Beschreibung wurde zu " + ChatColor.GOLD + ChatColor.BOLD + result + " §bgeändert!");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if(!gamer.hasLogined()){
            openProfileSettings(gamer, false);
        }
    }

    @EventHandler
    public void onInvetoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(PROFILE_SETTINGS_NAME)){
            return;
        }

        event.setCancelled(true);
        if(event.getCurrentItem() == null){
            return;
        }
        if(!event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.RIGHT)){
            return;
        }

        Player player = (Player) event.getWhoClicked();

        switch(event.getCurrentItem().getType()){
            case LIME_STAINED_GLASS:
                signGUI.openAgeSign(player);
                break;
            case PURPLE_STAINED_GLASS:
                signGUI.openDescriptionSign(player);
                break;
            default:

                break;
        }
    }
}
