package de.diadumenius.gamecenter.functions;

import de.diadumenius.gamecenter.Configs.PlayerConfig;
import de.diadumenius.gamecenter.Main;
import de.diadumenius.gamecenter.util.Gamer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;

// this class was created by Diadumenius
public class PlayerFunctions {

    public static int TimerSched;

    public static void startTimer(){
        TimerSched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {

                for(Player players : Bukkit.getOnlinePlayers()) {
                    if(PlayerConfig.contains("PlayerData." + players.getUniqueId() + ".minutes")) {

                        int hours;
                        int minutes;

                        if(PlayerConfig.contains("PlayerData." + players.getUniqueId() + ".hours")) {
                            hours = (int) PlayerConfig.get("PlayerData." + players.getUniqueId() + ".hours");
                        }else
                            hours = 0;
                        minutes = (int) PlayerConfig.get("PlayerData." + players.getUniqueId() + ".minutes");

                        minutes++;

                        try {
                            PlayerConfig.set("PlayerData." + players.getUniqueId() + ".minutes", minutes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (minutes == 60) {

                            minutes = 0;

                            try {
                                PlayerConfig.set("PlayerData." + players.getUniqueId() + ".minutes", minutes);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            hours++;
                            try {
                                PlayerConfig.set("PlayerData." + players.getUniqueId() + ".hours", hours);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        try {
                            PlayerConfig.set("PlayerData." + players.getUniqueId() + ".minutes", 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            PlayerConfig.set("PlayerData." + players.getUniqueId() + ".hours", 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 20*60, 20*60);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){
                    setTablist(players);
                }
            }
        },20*60,0);
    }

    public static void setTablist(Player player){
        Gamer gamer = Gamer.getGamerFromPlayer(player);
        player.setPlayerListFooter("\n§bDeine §3Spielzeit: §6§n" + gamer.getTimePlayed() + "\n\n§bBitte §3nicht §bspamen!\n" );
        player.setPlayerListHeader("\n  §bHerzlich Willkommen auf  \n§6§nGameCenter\n");
    }

    public static void updatePlayerData(Player player){

        try {
            PlayerConfig.set("PlayerData." + player.getUniqueId() + ".name", player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ItemStack getPlayerSkull(Player paramPlayer) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(paramPlayer.getName());
        skull.setItemMeta(meta);
        return skull;
    }

}
