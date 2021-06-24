package de.diadumenius.gamecenter.commands;

import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.Role;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// this class was created by Diadumenius
public class spawnCommand implements CommandExecutor, TabCompleter {

    private final String GAMECENTER_PREFIX = PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA);
    private final String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if(gamer.getRole().equals(Role.OP)){
                if(args.length == 2){
                    if(args[0].equals("set")){
                        switch(args[1]){
                            case "main-lobby":
                                LocationsConfig.setPlayerLocation("Locations.Spawns.main-lobby", player.getLocation());
                                player.sendMessage(GAMECENTER_PREFIX +  "§bDu hast die Spawn-Position für die §6§lHauptlobby §bgesetzt!");
                                break;
                            case "survival-world":
                                LocationsConfig.setPlayerLocation("Locations.Spawns.survival-world", player.getLocation());
                                player.sendMessage(GAMECENTER_PREFIX +  "§bDu hast die Spawn-Position für die §6§lSurvival-World §bgesetzt!");
                                break;
                        }
                    }
                }else
                    player.sendMessage(ERROR_PREFIX + "§cZu viele Argumente!");
            }else
                player.sendMessage(ERROR_PREFIX + "§cDu hast nicht genug Rechte!");
        }else
            sender.sendMessage(ERROR_PREFIX + "§cDu bist kein Spieler!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Player player = (Player) sender;
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if(gamer.getRole().equals(Role.OP)) {
            switch (args.length) {
                case 1:
                    list.clear();
                    list.add("set");
                    break;
                case 2:
                    list.clear();
                    list.add("main-lobby");
                    list.add("survival-world");
                    break;
            }
        }
        return list;
    }
}
