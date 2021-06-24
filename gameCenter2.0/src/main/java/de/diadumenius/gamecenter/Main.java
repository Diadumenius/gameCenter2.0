package de.diadumenius.gamecenter;

import de.diadumenius.gamecenter.Configs.Config;
import de.diadumenius.gamecenter.Configs.InventoryConfig;
import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.Configs.PlayerConfig;
import de.diadumenius.gamecenter.commands.*;
import de.diadumenius.gamecenter.functions.PlayerFunctions;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import de.diadumenius.gamecenter.listeners.GamerProtectionListener;
import de.diadumenius.gamecenter.listeners.JoinListener;
import de.diadumenius.gamecenter.listeners.TeleportInventoryListener;
import de.diadumenius.gamecenter.util.Gamer;
import de.diadumenius.gamecenter.util.LoginProcess;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public final class Main extends JavaPlugin{

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        new PlayerConfig();
        new Config();
        new LocationsConfig();
        new InventoryConfig();

        Gamer.setupGamerList();

        PluginFunctions.sendCommandPrefix();

        commandRegistration();
        listenerRegistration();

        PlayerFunctions.startTimer();

        ArrayList<Gamer> gamers = new ArrayList<>();
        for (Player players : Bukkit.getOnlinePlayers()){
            gamers.add(Gamer.getGamerFromPlayer(players));
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(PlayerFunctions.TimerSched);
        ArrayList<Gamer> gamers = new ArrayList<>();
        for (Player players : Bukkit.getOnlinePlayers()){
            gamers.add(Gamer.getGamerFromPlayer(players));
        }
    }

    private void commandRegistration(){
        registerCommand("save", new saveCommand());
        registerCommand("build", new buildCommand());
        registerCommand("heal", new healCommand());
        registerCommand("spawn", new spawnCommand());
        registerCommand("role", new RoleCommand());
        registerCommand("hub", new hubCommand());
        getCommand("hub").setTabCompleter(new hubCommand());
        getCommand("role").setTabCompleter(new RoleCommand());
        getCommand("spawn").setTabCompleter(new spawnCommand());
        getCommand("heal").setTabCompleter(new healCommand());
        getCommand("build").setTabCompleter(new buildCommand());
        getCommand("save").setTabCompleter(new saveCommand());
    }

    private void listenerRegistration(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new GamerProtectionListener(), this);
        pluginManager.registerEvents(new LoginProcess(), this);
        pluginManager.registerEvents(new TeleportInventoryListener(), this);
    }

    private void registerCommand(String name, CommandExecutor command){
        Objects.requireNonNull(getCommand(name)).setExecutor(command);
    }


    public static Main getPlugin() {
        return plugin;
    }
}
