package de.diadumenius.gamecenter.commands;

import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.GameState;
import de.diadumenius.gamecenter.util.Gamer;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// this class was created by Diadumenius
public class hubCommand implements CommandExecutor, TabCompleter {

    private final String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if(args.length == 0){
                gamer.teleportToMainLobby();
            }else
                player.sendMessage(ERROR_PREFIX + "§cZu viele Argumente!");
        }else
            sender.sendMessage(ERROR_PREFIX + "§cDu bist kein Spieler!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
