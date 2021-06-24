package de.diadumenius.gamecenter.listeners;

import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.SignGUI.signGUI;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.GameState;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

// this class was created by Diadumenius
public class TeleportInventoryListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getMaterial().equals(Material.IRON_PICKAXE)){
            if(Gamer.getGamerFromPlayer(event.getPlayer()).getGameState().equals(GameState.MAIN_LOBBY) ||
                    Gamer.getGamerFromPlayer(event.getPlayer()).getGameState().equals(GameState.BUILDER)){
                if(event.getItem().getItemMeta().getDisplayName().equals("§3Teleporter")){
                    event.setCancelled(true);
                    openTeleportInventory(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals("§bTeleporter")){
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
            case GRASS_BLOCK:
                Gamer gamer = Gamer.getGamerFromPlayer(player);
                gamer.setModeForGamestate(GameState.SURVIVAL);
                break;
            case HEART_OF_THE_SEA:
                gamer = Gamer.getGamerFromPlayer(player);
                gamer.teleportToMainLobby();
                break;
            default:

                break;
        }
    }

    public static void openTeleportInventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§bTeleporter");

        ArrayList list = new ArrayList();

        list.add("\n");
        list.add(ChatColor.DARK_GREEN + "  • Spieler online: " + "§a§l" + PluginFunctions.getGameStatePlayers(GameState.SURVIVAL).size() + "  ");

        inv.setItem(10, new ItemBuilder("§a Survival World", list, false, Material.GRASS_BLOCK).create());

        list.clear();
        list.add("\n");
        list.add(ChatColor.DARK_AQUA + "  • Spieler online: " + "§b§l" + PluginFunctions.getGameStatePlayers(GameState.MAIN_LOBBY).size() + "  ");

        inv.setItem(11, new ItemBuilder("§b Main Lobby", list, false, Material.HEART_OF_THE_SEA).create());

        player.openInventory(inv);
    }

}
