package de.diadumenius.gamecenter.listeners;

import de.diadumenius.gamecenter.functions.PlayerFunctions;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.GameState;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.LoginProcess;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

// this class was created by Diadumenius
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Gamer gamer = new Gamer(player);

        PlayerFunctions.updatePlayerData(player);

        event.setJoinMessage(null);

        TextComponent joinMessage = new TextComponent(PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA) + "§bDer Spieler ");
        TextComponent joinMessage2 = new TextComponent(" §bhat den Server betreten!");
        joinMessage.addExtra(gamer.getTextComponenet());
        joinMessage.addExtra(joinMessage2);

        for(Player players : Bukkit.getOnlinePlayers()) {
            players.spigot().sendMessage(joinMessage);
        }
        if(gamer.getGameState().equals(GameState.BUILDER)) {
            gamer.setModeForGamestate(GameState.BUILDER);
        }else
            gamer.setModeForGamestate(GameState.MAIN_LOBBY);

        if(!gamer.hasLogined()){
            LoginProcess.openProfileSettings(gamer, true);
        }


        TextComponent first = new TextComponent(ChatColor.DARK_GREEN + "Dein Status: §a");
        first.addExtra(gamer.getGameState().getTextComponent());
        player.spigot().sendMessage(first);
        PlayerFunctions.setTablist(player);
    }

}
