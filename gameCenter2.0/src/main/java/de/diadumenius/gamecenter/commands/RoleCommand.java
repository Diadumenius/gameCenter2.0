package de.diadumenius.gamecenter.commands;

import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.Role;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// this class was created by Diadumenius
public class RoleCommand implements CommandExecutor, TabCompleter {

    private final String GAMECENTER_PREFIX = PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA);
    private final String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if(gamer.getRole().equals(Role.OP)){
                if(args.length == 3 || args.length == 2){
                        if(args[0].equals("set") || args[0].equals("get")){
                                Role role = null;
                                switch (args[1]){
                                    case "op":
                                        role = Role.OP;
                                        break;
                                    case "vip":
                                        role = Role.VIP;
                                        break;
                                    case "starter":
                                        role = Role.STARTER;
                                        break;

                                }
                                switch(args[0]){
                                    case "set":
                                        if(args[1].equals("op") || args[1].equals("vip") || args[1].equals("starter")) {
                                            Player p = Bukkit.getPlayer(args[2]);
                                            if(p != null) {
                                                Gamer target = Gamer.getGamerFromPlayer(p);
                                                Role oldRole = target.getRole();
                                                if (!oldRole.equals(role)) {
                                                    target.setRole(role);
                                                    target.getPlayer().sendMessage(GAMECENTER_PREFIX + "§bDeine Rolle wurde von §6" + oldRole.getTranslation() + " §bzu §6" + role.getTranslation() + " §bgeändert.");
                                                    TextComponent first = new TextComponent(GAMECENTER_PREFIX + "§bDie Rolle von ");
                                                    TextComponent second = new TextComponent(" §bwurde von §6" + oldRole.getTranslation() + " §bzu §6" + role.getTranslation() + " §bgeändert.");
                                                    first.addExtra(target.getTextComponenet());
                                                    first.addExtra(second);
                                                    player.spigot().sendMessage(first);
                                                    target.saveDataInConfig(p);
                                                } else {
                                                    TextComponent first = new TextComponent(GAMECENTER_PREFIX + "§bDer Spieler ");
                                                    TextComponent second = new TextComponent(" §bhat die Rolle §6" + role.getTranslation() + " §bbereits.");
                                                    first.addExtra(target.getTextComponenet());
                                                    first.addExtra(second);
                                                    player.spigot().sendMessage(first);
                                                }
                                            }else
                                                player.sendMessage(ERROR_PREFIX + "§cDieser Spieler existiert nicht!");
                                        }else
                                            player.sendMessage(ERROR_PREFIX + "§7-> §6role set/get starter/vip/op <PLAYER>");
                                        break;
                                    case "get":
                                        Player p = Bukkit.getPlayer(args[1]);
                                        if(p != null){
                                            Gamer target = Gamer.getGamerFromPlayer(p);
                                            TextComponent first = new TextComponent(GAMECENTER_PREFIX);
                                            first.addExtra(target.getTextComponenet());
                                            TextComponent second = new TextComponent(" §bhat die Rolle §6" + target.getRole().getTranslation() + "§b!");
                                            first.addExtra(second);

                                            player.spigot().sendMessage(first);
                                        }else
                                            player.sendMessage(ERROR_PREFIX + "§cDieser Spieler existiert nicht!");
                                        break;
                                }
                        }else
                            player.sendMessage(ERROR_PREFIX + "§7-> §6role set/get starter/vip/op <PLAYER>");
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
        if(gamer.getRole().equals(Role.OP)){
            switch(args.length){
                case 1:
                    list.clear();
                    list.add("set");
                    list.add("get");
                    break;
                case 2:
                    switch(args[0]){
                        case "set":
                            list.clear();
                            list.add("starter");
                            list.add("vip");
                            list.add("op");
                            break;
                        case "get":
                            list.clear();
                            for(Player players : Bukkit.getOnlinePlayers()){
                                list.add(players.getName());
                            }
                            break;
                    }
                    break;
                case 3:
                    switch(args[0]){
                        case "set":
                            list.clear();
                            for(Player players : Bukkit.getOnlinePlayers()){
                                list.add(players.getName());
                            }
                            break;
                        default:
                            list.clear();
                            break;
                    }
                    break;

            }

        }
        return list;
    }
}
