package de.diadumenius.gamecenter.listeners;

import de.diadumenius.gamecenter.util.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

// this class was created by Diadumenius
public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        gamer.saveGameStateStatus();
    }
}
