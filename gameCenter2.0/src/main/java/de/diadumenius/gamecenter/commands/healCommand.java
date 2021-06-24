package de.diadumenius.gamecenter.commands;

import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.Role;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
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
public class healCommand implements CommandExecutor, TabCompleter {

    private final String GAMECENTER_PREGFIX  = PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA);
    private final String ERROR_PREFIX = PluginFunctions.generatePrefix("Error", ChatColor.DARK_RED);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Gamer gamer = Gamer.getGamerFromPlayer(player);
            if(gamer.getRole().equals(Role.OP) ||gamer.getRole().equals(Role.VIP)){
                if(args.length <= 1){
                    switch(args.length){
                        case 0:
                            gamer.heal();
                            player.sendMessage(GAMECENTER_PREGFIX  + "§bDu wurdest geheilt!");
                            break;
                        case 1:
                            String arg1 = args[0];
                            ArrayList<String> names = new ArrayList<>();
                            for(Player players : Bukkit.getOnlinePlayers()){
                                names.add(players.getName());
                            }
                            if(names.contains(arg1)){
                                Player target = Bukkit.getPlayer(arg1);
                                Gamer tg = Gamer.getGamerFromPlayer(target);
                                tg.heal();
                                net.md_5.bungee.api.chat.TextComponent tc1 = new TextComponent(GAMECENTER_PREGFIX + "§bDu wurdest von ");
                                TextComponent tc2 = new TextComponent(" §bgeheilt");
                                tc1.addExtra(gamer.getTextComponenet());
                                tc1.addExtra(tc2);
                                target.spigot().sendMessage(tc1);

                                net.md_5.bungee.api.chat.TextComponent pc1 = new TextComponent(GAMECENTER_PREGFIX + "§bDu hast ");
                                TextComponent pc2 = new TextComponent(" §bgeheilt");
                                pc1.addExtra(tg.getTextComponenet());
                                pc1.addExtra(pc2);
                                player.spigot().sendMessage(pc1);
                            }else
                                player.sendMessage(ERROR_PREFIX + "§cSpieler nicht gefunden!");
                            break;
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
        if(gamer.getRole().equals(Role.OP) ||gamer.getRole().equals(Role.VIP)){
            switch (args.length){
                case 1:
                    for(Player players : Bukkit.getOnlinePlayers()){
                        list.add(players.getName());
                    }
                    break;
            }
        }
        return list;
    }
}
