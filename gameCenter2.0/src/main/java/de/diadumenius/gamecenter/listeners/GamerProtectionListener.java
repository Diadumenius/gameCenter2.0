package de.diadumenius.gamecenter.listeners;

import de.diadumenius.gamecenter.util.Gamer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;

// this class was created by Diadumenius
public class GamerProtectionListener implements Listener {

    @EventHandler
    public void onBuild(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if(!gamer.getCanBuild()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if (!gamer.getCanGetDamage()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMakeDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if (!gamer.getCanMakeDamage()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if (!gamer.getCanFoodLevelChange()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanDestroy()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanMove()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanDropItem()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanGameModeChange()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanChangeWorld()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanChat()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanFly()) {
            if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanBedEnter()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanPickupItems()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if (!gamer.getCanCraft()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWaterPlace(PlayerBucketEmptyEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanWaterPlace()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanBucketFill()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBeTarget(EntityTargetEvent event){
        if(event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if (!gamer.getCanBeTarget()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if (!gamer.getCanInteract()) {
            event.setCancelled(true);
        }
    }

}
