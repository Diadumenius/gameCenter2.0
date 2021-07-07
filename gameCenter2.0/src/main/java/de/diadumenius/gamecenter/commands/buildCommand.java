package de.diadumenius.gamecenter.commands;

import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.GameState;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.Role;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// this class was created by Diadumenius
public class buildCommand implements CommandExecutor, TabCompleter {

    private final String BUILD_PREFIX = PluginFunctions.generatePrefix("Build", ChatColor.DARK_GREEN);
    private final String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if (gamer.getRole().equals(Role.OP) || gamer.getRole().equals(Role.VIP)) {
                if (args.length == 0) {
                    if (!gamer.getGameState().equals(GameState.BUILDER)) {
                        gamer.setModeForGamestate(GameState.BUILDER);

                        player.sendMessage(BUILD_PREFIX + ChatColor.GREEN + "Du bist nun ein Builder!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3, 2);
                    } else {
                        gamer.setModeForGamestate(GameState.MAIN_LOBBY);
                        if (player.isFlying()) {
                            player.setFlying(false);
                        }
                        player.sendMessage(BUILD_PREFIX + ChatColor.GREEN + "Du bist nun kein Builder mehr!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3, 2);
                    }
                } else {
                    player.sendMessage(ERROR_PREFIX + "§cZu viele Argumente!");
                }
            } else {
                player.sendMessage(ERROR_PREFIX + "§cDu hast nicht genügend Rechte!");
            }
        } else
            sender.sendMessage(PluginFunctions.ERROR_PREFIX + ChatColor.RED + "Du bist kein Spieler!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        return list;
    }

    //xD
}
