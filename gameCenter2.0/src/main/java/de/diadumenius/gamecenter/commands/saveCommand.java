package de.diadumenius.gamecenter.commands;

import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.SignGUI.signGUI;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.Role;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// this class was created by Diadumenius
public class saveCommand implements CommandExecutor, TabCompleter {

    private final String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);
    private final String GAMECENTRER_PREFIX = PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if(gamer.getRole().equals(Role.OP)){
                if(args.length < 4){
                    switch (args.length){
                        case 3:
                            String arg1 = args[0];
                            if(arg1.equals("Location")) {
                                switch (arg1) {
                                    case "Location":
                                        String arg2 = args[1];
                                        String arg3 = args[2];
                                        if(arg2.equals("normal") || arg2.equals("player")){
                                            switch(arg2){
                                                case "normal":
                                                    LocationsConfig.setLocation("Locations.Signs." + arg3, player.getLocation());
                                                    player.sendMessage(GAMECENTRER_PREFIX + "§bDu §7>> §6§l" + arg3 + " §7(Location)");
                                                    for(Player players : Bukkit.getOnlinePlayers()){
                                                        if(!players.getName().equals(player.getName())){
                                                            TextComponent tc1 = new TextComponent(GAMECENTRER_PREFIX + "§b ");
                                                            TextComponent tc2 = new TextComponent("  §7>> §6§l" + arg3 + " §7(Location)");
                                                            tc1.addExtra(gamer.getTextComponenet());
                                                            tc1.addExtra(tc2);
                                                            players.spigot().sendMessage(tc1);
                                                        }
                                                    }
                                                    break;
                                                case "player":
                                                    LocationsConfig.setPlayerLocation("Locations.Signs." + arg3, player.getLocation());
                                                    player.sendMessage(GAMECENTRER_PREFIX + "§bDu §7>> §6§l" + arg3 + " §7(Player-Location)");
                                                    for(Player players : Bukkit.getOnlinePlayers()){
                                                        if(!players.getName().equals(player.getName())){
                                                            TextComponent tc1 = new TextComponent(GAMECENTRER_PREFIX + "§b ");
                                                            TextComponent tc2 = new TextComponent("  §7>> §6§l" + arg3 + " §7(Player-Location)");
                                                            tc1.addExtra(gamer.getTextComponenet());
                                                            tc1.addExtra(tc2);
                                                            players.spigot().sendMessage(tc1);
                                                            players.sendMessage(GAMECENTRER_PREFIX + "§b " + player.getName() + "  §7>> §6§l" + arg3 + " §7(Player-Location)");
                                                        }
                                                    }
                                                    break;
                                            }
                                        }else{
                                            player.sendMessage(ERROR_PREFIX + "§cKein gültiger Location-Typ!");
                                        }
                                        break;
                                }
                            }else{
                                player.sendMessage(ERROR_PREFIX + "§cUngültiger Befehl");
                            }
                            break;
                    }
                }else{
                    player.sendMessage(ERROR_PREFIX + "§cZu viele Argumente!");
                }
            }else{
                player.sendMessage(ERROR_PREFIX + "§cDu hast nicht genügend Rechte!");
            }
        }else
            sender.sendMessage(ERROR_PREFIX + "§cDu bist kein Spieler!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        Player player = (Player) sender;
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        if(gamer.getRole().equals(Role.OP)){
            switch(args.length){
                case 1:
                    list.clear();
                    list.add("Location");
                    break;
                case 2:
                    switch(args[0]){
                        case "Location":
                            list.clear();
                            list.add("normal");
                            list.add("player");
                            break;
                    }
                    break;
            }
        }
        return list;
    }
}
