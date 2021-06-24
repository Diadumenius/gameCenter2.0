package de.diadumenius.gamecenter.functions;

import de.diadumenius.gamecenter.util.GameState;
import de.diadumenius.gamecenter.util.Gamer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

// this class was created by Diadumenius
public class PluginFunctions {

    public static final String ERROR_PREFIX = generatePrefix("Error", ChatColor.DARK_RED);

    public static void sendCommandPrefix(){
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§a |        * *     ___   {[´|`]}   ");
        Bukkit.getConsoleSender().sendMessage("§a |       |   |   (         ~      ");
        Bukkit.getConsoleSender().sendMessage("§a |       |   |   |                ");
        Bukkit.getConsoleSender().sendMessage("§a |_____  (___)   (___/       @c   ");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public static String generatePrefix(String title, ChatColor color){
        return "§8|" + color + title + "§7§o>> §r";
    }

    public static ArrayList<Player> getGameStatePlayers(GameState state){
        ArrayList<Player> list = new ArrayList<>();

        for(Player players: Bukkit.getOnlinePlayers()){
            Gamer gamer = Gamer.getGamerFromPlayer(players);
            if(gamer.getGameState().equals(state)){
                list.add(players);
            }
        }

        return list;
    }
}
