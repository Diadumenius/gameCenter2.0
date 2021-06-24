package de.diadumenius.gamecenter.util;

import de.diadumenius.gamecenter.Configs.InventoryConfig;
import de.diadumenius.gamecenter.Configs.LocationsConfig;
import de.diadumenius.gamecenter.Configs.PlayerConfig;
import de.diadumenius.gamecenter.functions.PlayerFunctions;
import de.diadumenius.gamecenter.functions.PluginFunctions;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;

// this class was created by Diadumenius
public class Gamer {

    private static ArrayList<String> gamers;
    private final String GAMECENTER_PREFIX = PluginFunctions.generatePrefix("GameCenter", ChatColor.DARK_AQUA);

    private Player player;
    private Role role;
    private String gameName;
    private String description;
    private int age = 0;
    private GameState gameState;

    private Boolean canBuild;
    private Boolean canDestroy;
    private Boolean canGetDamage;
    private Boolean canMakeDamage;
    private Boolean canFoodLevelChange;
    private Boolean canMove;
    private Boolean canDropItem;
    private Boolean canGameModeChange;
    private Boolean canChangeWorld;
    private Boolean canChat;
    private Boolean canFly;
    private Boolean canBedEnter;
    private Boolean canPickupItems;
    private Boolean canCraft;
    private Boolean canWaterPlace;
    private Boolean canBucketFill;
    private Boolean canBeTarget;
    private Boolean canInteract;

    public Gamer(Player player){
        this.gameName = player.getName();
        this.player =  player;

        role = getRole();
        description = getDescription();
        age = getAge();
        gameState = getGameState();

        gamers = (ArrayList<String>) PlayerConfig.get("Gamer.GamersList");

        if(!gamers.contains(this.getGameName())){
            gamers.add(this.getGameName());
            try {
                PlayerConfig.set("Gamer.GamersList", gamers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTimePlayed(){
        int minutes;
        int hours;
        if(PlayerConfig.contains("PlayerData." + this.getPlayer().getUniqueId() + ".minutes")) {
            minutes = (int) PlayerConfig.get("PlayerData." + this.getPlayer().getUniqueId() + ".minutes");
        }else
            return null;
        if(PlayerConfig.contains("PlayerData." + this.getPlayer().getUniqueId() + ".hours")) {
            hours = (int) PlayerConfig.get("PlayerData." + this.getPlayer().getUniqueId() + ".hours");
        }else
            return null;
        String time = hours + ":" + minutes;
        return time;
    }

    private void setMainLobbyInventory(){
        ItemStack item8 = PlayerFunctions.getPlayerSkull(this.player);
        ItemMeta meta8 = item8.getItemMeta();
        meta8.setDisplayName("§6Profil-Einstellungen");
        item8.setItemMeta(meta8);

        ItemStack item4 = new ItemBuilder("§3Teleporter", null, false, Material.IRON_PICKAXE).create();

        this.player.getInventory().clear();
        this.player.getInventory().setItem(8, item8);
        this.player.getInventory().setItem(4, item4);

        this.player.updateInventory();
    }

    private void setGameStateStatus(){
        if(this.getGameState().equals(GameState.MAIN_LOBBY)){
            Location loc = LocationsConfig.getLocation("Locations.Spawns.main-lobby");
            if(LocationsConfig.getLocation("Locations." + this.getGameState().getTranslation() + "." + this.getGameName()) != null){
                loc = LocationsConfig.getLocation("Locations." + this.getGameState().getTranslation() + "." + this.getGameName());
            }
            Double health = 20.0;
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".health") != null){
                health = (Double) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".health");
            }
            int foodLevel = 20;
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".foodLevel") != null){
                foodLevel = (int) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".foodLevel");
            }
            int level = 0;
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".xp") != null){
                level = (int) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".xp");
            }

            this.player.teleport(loc);
            this.player.setHealth(health);
            this.player.setFoodLevel(foodLevel);
            this.player.setLevel(level);
            this.player.setGameMode(GameMode.SURVIVAL);

            if(this.getGameState().equals(GameState.MAIN_LOBBY)){
                this.setMainLobbyInventory();
            }

            if(this.getGameState().equals(GameState.SURVIVAL)){
                if(InventoryConfig.contains("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory")) {
                    this.player.getInventory().setContents((ItemStack[]) InventoryConfig.config.get("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory", player.getInventory().getContents()));
                }else
                    this.player.getInventory().clear();
            }

        }else if (this.getGameState().equals(GameState.SURVIVAL)){
            Location loc = LocationsConfig.getLocation("Locations.Spawns.survival-world");
            if(LocationsConfig.getLocation("Locations." + this.getGameState().getTranslation() + "." + this.getGameName()) != null){
                loc = LocationsConfig.getLocation("Locations." + this.getGameState().getTranslation() + "." + this.getGameName());
            }
            Double health = 20.0;
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".health") != null){
                health = (Double) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".health");
            }
            int foodLevel = 20;
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".foodLevel") != null){
                foodLevel = (int) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".foodLevel");
            }
            int level = 0;
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".xp") != null){
                level = (int) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".xp");
            }

            this.player.teleport(loc);
            this.player.setHealth(health);
            this.player.setFoodLevel(foodLevel);
            this.player.setLevel(level);
            this.player.setGameMode(GameMode.SURVIVAL);

            if(this.getGameState().equals(GameState.MAIN_LOBBY)){
                this.setMainLobbyInventory();
            }

            if(this.getGameState().equals(GameState.SURVIVAL)){
                if(InventoryConfig.contains("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory")) {
                    this.player.getInventory().setContents((ItemStack[]) InventoryConfig.config.get("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory", player.getInventory().getContents()));
                }else
                    this.player.getInventory().clear();
            }

        }else if(this.getGameState().equals(GameState.BUILDER)){
            String gameModeName = "survival";
            if(PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".GameMode") != null){
                gameModeName = (String) PlayerConfig.get("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".GameMode");
            }
            GameMode gameMode;
            switch (gameModeName){
                case "creative":
                    gameMode = GameMode.CREATIVE;
                    break;
                case "spectator":
                    gameMode = GameMode.SPECTATOR;
                    break;
                case "adventure":
                    gameMode = GameMode.ADVENTURE;
                    break;
                default:
                    gameMode = GameMode.SURVIVAL;
                    break;
            }
            this.player.setGameMode(gameMode);

            if(InventoryConfig.contains("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory")) {
                this.player.getInventory().setContents((ItemStack[]) InventoryConfig.config.get("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory", player.getInventory().getContents()));
            }else
                this.player.getInventory().clear();
        }
    }

    private void saveGameMode(){
        if(this.getGameState().equals(GameState.BUILDER)){
            try {
                PlayerConfig.set("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".GameMode", this.player.getGameMode().toString().toLowerCase());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveInventory(){
        if(this.getGameState().equals(GameState.BUILDER) || this.getGameState().equals(GameState.SURVIVAL)){
            InventoryConfig.saveInventoryContents("Contents." + this.getGameName() + "." + this.getGameState().getTranslation() + ".Inventory", this.player.getInventory());
        }
    }

    private void saveGameStateLocation(){
        if(this.getGameState().equals(GameState.MAIN_LOBBY) ||
                this.getGameState().equals(GameState.SURVIVAL)){
            LocationsConfig.setPlayerLocation("Locations." + this.getGameState().getTranslation() + "." + this.getGameName(), this.player.getLocation());
        }
    }

    public void saveGameStateStatus(){
        if(this.getGameState().equals(GameState.MAIN_LOBBY) ||
                this.getGameState().equals(GameState.SURVIVAL)){
            saveGameStateLocation();
            try {
                PlayerConfig.set("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".health", this.player.getHealth());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PlayerConfig.set("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".foodLevel", this.player.getFoodLevel());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PlayerConfig.set("Gamer." + this.getGameName() + ".GameStatus." + this.getGameState().getTranslation() + ".xp", this.player.getLevel());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(this.getGameState().equals(GameState.SURVIVAL)){
                saveInventory();
            }

        }else if(this.getGameState().equals(GameState.BUILDER)){
            saveInventory();
            saveGameMode();
        }
    }

    public TextComponent getTextComponenet(){
        TextComponent playerMessage = new TextComponent("§6" + player.getName());
        playerMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7<<§6§n" + player.getName() + "§7>>")
                .append("\n").append(ChatColor.LIGHT_PURPLE + this.getDescription())
                .append("\n")
                .append("\n").append(ChatColor.DARK_AQUA + "  - Rolle: §b" + this.getRole().getTranslation())
                .append("\n").append(ChatColor.DARK_AQUA + "  - Alter: §b" + this.getAge())
                .append("\n").append(ChatColor.DARK_AQUA + "  - Status: §b" + this.getGameState().getTranslation())
                .append("\n")
                .create()));
        return playerMessage;
    }

    public void heal(){
        this.getPlayer().setHealth(20);
        this.getPlayer().setFoodLevel(20);
    }

    public Boolean hasLogined(){
        Boolean hasLogined = true;
        if(this.getDescription().equals(" ") ||
            this.getAge() == 0){
            hasLogined = false;
        }
        return hasLogined;
    }

    public void setModeForGamestate(GameState gamestate){
        switch(gamestate){
            case MAIN_LOBBY:
                this.saveGameStateStatus();
                this.setGameState(gamestate);
                this.setCanBedEnter(false);
                this.setCanDestroy(false);
                this.setCanBeTarget(false);
                this.setCanBuckteFill(false);
                this.setCanBuild(false);
                this.setCanChangeWorld(false);
                this.setCanChat(true);
                this.setCanCraft(false);
                this.setCanDropItem(false);
                this.setCanFly(false);
                this.setCanFoodLevelChange(false);
                this.setCanGameModeChange(true);
                this.setCanGetDamage(false);
                this.setCanInteract(true);
                this.setCanMakeDamage(false);
                this.setCanMove(true);
                this.setCanPickupItems(false);
                this.setCanWaterPlace(false);
                this.saveDataInConfig(player);

                this.setGameStateStatus();

                TextComponent first = new TextComponent(GAMECENTER_PREFIX + "§bSenden zur §6");
                first.addExtra(gamestate.getTextComponent());
                TextComponent third = new TextComponent("§b...");
                first.addExtra(third);

                player.spigot().sendMessage(first);

                break;
            case SURVIVAL:
                this.saveGameStateStatus();
                this.setGameState(gamestate);
                this.setCanBedEnter(true);
                this.setCanDestroy(true);
                this.setCanBeTarget(true);
                this.setCanBuckteFill(true);
                this.setCanBuild(true);
                this.setCanChangeWorld(true);
                this.setCanChat(true);
                this.setCanCraft(true);
                this.setCanDropItem(true);
                this.setCanFly(true);
                this.setCanFoodLevelChange(true);
                this.setCanGameModeChange(true);
                this.setCanGetDamage(true);
                this.setCanInteract(true);
                this.setCanMakeDamage(true);
                this.setCanMove(true);
                this.setCanPickupItems(true);
                this.setCanWaterPlace(true);
                this.saveDataInConfig(player);

                this.setGameStateStatus();

                TextComponent first1 = new TextComponent(GAMECENTER_PREFIX + "§bSenden zur §6");
                first1.addExtra(gamestate.getTextComponent());
                TextComponent third1 = new TextComponent("§b...");
                first1.addExtra(third1);

                player.spigot().sendMessage(first1);

                break;
            case BUILDER:
                this.saveGameStateStatus();
                this.setGameState(gamestate);
                this.setBuildMode();

                this.setGameStateStatus();
                break;
        }
    }

    public void setBuildMode(){
        this.setCanBedEnter(true);
        this.setCanDestroy(true);
        this.setCanBeTarget(true);
        this.setCanBuckteFill(true);
        this.setCanBuild(true);
        this.setCanChangeWorld(true);
        this.setCanChat(true);
        this.setCanCraft(true);
        this.setCanDropItem(true);
        this.setCanFly(true);
        this.setCanFoodLevelChange(true);
        this.setCanGameModeChange(true);
        this.setCanGetDamage(true);
        this.setCanInteract(true);
        this.setCanMakeDamage(true);
        this.setCanMove(true);
        this.setCanPickupItems(true);
        this.setCanWaterPlace(true);

        this.saveDataInConfig(player);
    }

    public void setAllPermissionsTo(Boolean b){
        this.setCanBedEnter(b);
        this.setCanDestroy(b);
        this.setCanBeTarget(b);
        this.setCanBuckteFill(b);
        this.setCanBuild(b);
        this.setCanChangeWorld(b);
        this.setCanChat(b);
        this.setCanCraft(b);
        this.setCanDropItem(b);
        this.setCanFly(b);
        this.setCanFoodLevelChange(b);
        this.setCanGameModeChange(b);
        this.setCanGetDamage(b);
        this.setCanInteract(b);
        this.setCanMakeDamage(b);
        this.setCanMove(b);
        this.setCanPickupItems(b);
        this.setCanWaterPlace(b);

        this.saveDataInConfig(player);
    }

    public void teleportToMainLobby(){
        Location loc = LocationsConfig.getLocation("Locations.Spawns.main-lobby");
        this.saveGameStateStatus();
        this.setGameState(GameState.MAIN_LOBBY);
        this.setCanBedEnter(false);
        this.setCanDestroy(false);
        this.setCanBeTarget(false);
        this.setCanBuckteFill(false);
        this.setCanBuild(false);
        this.setCanChangeWorld(false);
        this.setCanChat(true);
        this.setCanCraft(false);
        this.setCanDropItem(false);
        this.setCanFly(false);
        this.setCanFoodLevelChange(false);
        this.setCanGameModeChange(true);
        this.setCanGetDamage(false);
        this.setCanInteract(true);
        this.setCanMakeDamage(false);
        this.setCanMove(true);
        this.setCanPickupItems(false);
        this.setCanWaterPlace(false);
        this.saveDataInConfig(player);

        this.player.teleport(loc);
        this.player.setHealth(20.0);
        this.player.setFoodLevel(20);
        this.player.setLevel(0);
        this.player.setGameMode(GameMode.SURVIVAL);

        this.setMainLobbyInventory();

        TextComponent first = new TextComponent(GAMECENTER_PREFIX + "§bSenden zur §6");
        first.addExtra(GameState.MAIN_LOBBY.getTextComponent());
        TextComponent third = new TextComponent("§b...");
        first.addExtra(third);

        player.spigot().sendMessage(first);
    }

    public static void setupGamerList() {
        if (!PlayerConfig.contains("Gamer.GamersList")) {
            try {
                PlayerConfig.set("Gamer.GamersList", new ArrayList<Gamer>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Gamer getGamerFromPlayer(Player player){
        Gamer gamer = null;

        gamers = (ArrayList<String>) PlayerConfig.get("Gamer.GamersList");

        if(gamers.contains(player.getName())){
            gamer = getGamerFromName(player.getName());
        }

        return gamer;
    }

    public static Gamer getGamerFromName(String name){
        Gamer gamer = null;

        if(PlayerConfig.contains("Gamer." + name)){
            gamer = new Gamer(Bukkit.getPlayer(name));
        }
        return gamer;
    }

    public void saveDataInConfig(Player player){

        if(this.gameName == null){
            this.gameName = player.getName();
        }
        if(this.player == null){
            this.player = player;
        }
        if(this.role == null){
            this.role = this.getRole();
        }
        if(this.gameState == null){
            this.gameState = this.getGameState();
        }
        if(this.description == null){
            this.description = this.getDescription();
        }
        if(this.canWaterPlace == null){
            this.canWaterPlace = this.getCanWaterPlace();
        }
        if(this.canPickupItems == null){
            this.canPickupItems = this.getCanPickupItems();
        }
        if(this.canMove == null){
            this.canMove = this.getCanMove();
        }
        if(this.canMakeDamage == null){
            this.canMakeDamage = this.getCanMakeDamage();
        }
        if(this.canInteract == null){
            this.canInteract = this.getCanInteract();
        }
        if(this.canGetDamage == null){
            this.canGetDamage = this.getCanGetDamage();
        }
        if(this.canGameModeChange == null){
            this.canGameModeChange = this.getCanGameModeChange();
        }
        if(this.canFoodLevelChange == null){
            this.canFoodLevelChange = this.getCanFoodLevelChange();
        }
        if(this.canFly == null){
            this.canFly = this.getCanFly();
        }
        if(this.canDropItem == null){
            this.canDropItem = this.getCanDropItem();
        }
        if(this.canDestroy == null){
            this.canDestroy = this.getCanDestroy();
        }
        if(this.canCraft == null){
            this.canCraft = this.getCanCraft();
        }
        if(this.canChat == null){
            this.canChat = this.getCanChat();
        }
        if(this.canChangeWorld== null){
            this.canChangeWorld = this.getCanChangeWorld();
        }
        if(this.canBuild == null){
            this.canBuild = this.getCanBuild();
        }
        if(this.canBucketFill == null){
            this.canBucketFill = this.getCanBucketFill();
        }
        if(this.canBeTarget == null){
            this.canBeTarget = this.getCanBeTarget();
        }
        if(this.canBedEnter == null){
            this.canBedEnter = this.getCanBedEnter();
        }
        if(this.age == 0){
            this.age = this.getAge();
        }

        try {
            PlayerConfig.set("Gamer." + this.gameName + ".UUID", Bukkit.getPlayer(this.gameName).getUniqueId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".Player", gameName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".Role", this.role.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".Gamename", this.gameName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".Description", this.description);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".Age", this.age);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".GameState", this.gameState.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canBuild", this.canBuild);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canGetDamage", this.canGetDamage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canMakeDamage", this.canMakeDamage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canFoodLevelChange", this.canFoodLevelChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canMove", this.canMove);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canDropItem", this.canDropItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canGamemodeChange", this.canGameModeChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canChangeWorld", this.canChangeWorld);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canChat", this.canChat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canFly", this.canFly);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canBedEnter", this.canBedEnter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canPickupItems", this.canPickupItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canCraft", this.canCraft);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canWaterPlace", this.canWaterPlace);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canBucketFill", this.canBucketFill);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canBetTarget", this.canBeTarget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canInteract", this.canInteract);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlayerConfig.set("Gamer." + this.gameName + ".canDestroy", this.canDestroy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public void setDescription(String description){
        this.description = description;
    }



    public void setAge(int age){
        this.age = age;
    }


    private void setGameState(GameState gameState){
        this.gameState = gameState;
    }


    public Role getRole() {
        Role role = Role.STARTER;

        if(PlayerConfig.contains("Gamer." + this.gameName + ".Role")) {
            String Role_Name;
            Role_Name = (String) PlayerConfig.get("Gamer." + this.gameName + ".Role");
            switch (Role_Name) {
                case "OP":
                    role = Role.OP;
                    break;
                case "VIP":
                    role = Role.VIP;
                    break;
                case "STARTER":
                    role = Role.STARTER;
                    break;
                default:

                    break;
            }
        }
        return role;
    }



    public String getDescription() {
        String description = " ";
        if(PlayerConfig.get("Gamer." + this.gameName + ".Description") != null){
            description = (String) PlayerConfig.get("Gamer." + this.gameName + ".Description");
        }
        return description;
    }

    public int getAge() {
        int age = 0;
        if(PlayerConfig.get("Gamer." + this.gameName + ".Age") != null){
            age = (int) PlayerConfig.get("Gamer." + this.gameName + ".Age");
        }
        return age;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getGameName() {
        return this.gameName;
    }

    public GameState getGameState() {
        GameState gameState = GameState.MAIN_LOBBY;
        if(PlayerConfig.get("Gamer." + this.gameName + ".GameState") != null) {
            String gameStateName = (String) PlayerConfig.get("Gamer." + this.gameName + ".GameState");
            switch (gameStateName) {
                case "MAIN_LOBBY":
                    gameState = GameState.MAIN_LOBBY;
                    break;
                case "BEDWARS_INGAME":
                    gameState = GameState.BEDWARS_INGAME;
                    break;
                case "BEDWARS_LOBBY":
                    gameState = GameState.BEDWARS_LOBBY;
                    break;
                case "BEDWARS_SPEC":
                    gameState = GameState.BEDWARS_SPEC;
                    break;
                case "SURVIVAL":
                    gameState = GameState.SURVIVAL;
                    break;
                case "BUILDER":
                    gameState = GameState.BUILDER;
                    break;
            }
        }
        return gameState;
    }

    public void setPlayerName(Player player) {
    }

    public Boolean getCanBuild() {
        Boolean canBuild = false;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canBuild") != null){
            canBuild = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canBuild");
        }
        return canBuild;
    }

    public void setCanBuild(Boolean canBuild) {
        this.canBuild = canBuild;
    }

    public Boolean getCanDestroy() {
        Boolean canDestroy = false;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canDestroy") != null){
            canDestroy = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canDestroy");
        }
        return canDestroy;
    }

    public void setCanDestroy(Boolean canDestroy) {
        this.canDestroy = canDestroy;
    }

    public Boolean getCanGetDamage() {
        Boolean canGetDamage = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canGetDamage") != null){
            canGetDamage = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canGetDamage");
        }
        return canGetDamage;
    }

    public void setCanGetDamage(Boolean canGetDamage) {
        this.canGetDamage = canGetDamage;

    }

    public Boolean getCanMakeDamage() {
        Boolean canMakeDamage = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canMakeDamage") != null){
            canMakeDamage = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canMakeDamage");
        }
        return canMakeDamage;
    }

    public void setCanMakeDamage(Boolean canMakeDamage) {
        this.canMakeDamage = canMakeDamage;
    }

    public Boolean getCanFoodLevelChange() {
        Boolean canFoodLevelChange = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canFoodLevelChange") != null){
            canFoodLevelChange = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canFoodLevelChange");
        }
        return canFoodLevelChange;
    }

    public void setCanFoodLevelChange(Boolean canfoodLevelChange) {
        this.canFoodLevelChange = canfoodLevelChange;
    }

    public Boolean getCanMove() {
        Boolean canMove = true;
        if(PlayerConfig.contains("Gamer." + this.gameName + ".canMove")){
            canMove = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canMove");
        }
        return canMove;
    }

    public void setCanMove(Boolean canMove) {
        this.canMove = canMove;
    }

    public Boolean getCanDropItem() {
        Boolean canDropItem = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canDropItem") != null){
            canDropItem = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canDropItem");
        }
        return canDropItem;
    }

    public void setCanDropItem(Boolean canDropItem) {
        this.canDropItem = canDropItem;
    }

    public Boolean getCanGameModeChange() {
        Boolean canGameModeChange = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canGameModeChange") != null){
            canGameModeChange = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canGameModeChange");
        }
        return canGameModeChange;
    }

    public void setCanGameModeChange(Boolean canGameModeChange) {
        this.canGameModeChange = canGameModeChange;
    }

    public Boolean getCanChangeWorld() {
        Boolean canChangeWorld = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canChangeWorld") != null){
            canChangeWorld = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canChangeWorld");
        }
        return canChangeWorld;
    }

    public ArrayList<String> getGamers(){
        gamers = (ArrayList<String>) PlayerConfig.get("Gamer.GamersList");
        return gamers;
    }

    public void setCanChangeWorld(Boolean canChangeWorld) {
        this.canChangeWorld = canChangeWorld;
    }

    public Boolean getCanChat() {
        Boolean canChat = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canChat") != null){
            canChat = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canChat");
        }
        return canChat;
    }

    public void setCanChat(Boolean canChat) {
        this.canChat = canChat;
    }

    public Boolean getCanFly() {
        Boolean canFly = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canFly") != null){
            canFly = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canFly");
        }
        return canFly;
    }

    public void setCanFly(Boolean canFly) {
        this.canFly = canFly;
    }

    public Boolean getCanBedEnter() {
        Boolean canBedEnter = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canBedEnter") != null){
            canBedEnter = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canBedEnter");
        }
        return canBedEnter;
    }

    public void setCanBedEnter(Boolean canBedEnter) {
        this.canBedEnter = canBedEnter;
    }

    public Boolean getCanPickupItems() {
        Boolean canPickupItems = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canPickupItems") != null){
            canPickupItems = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canPickupItems");
        }
        return canPickupItems;
    }

    public void setCanPickupItems(Boolean canPickupItems) {
        this.canPickupItems = canPickupItems;
    }

    public Boolean getCanCraft() {
        Boolean canCraft = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canCraft") != null){
            canCraft = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canCraft");
        }
        return canCraft;
    }

    public void setCanCraft(Boolean canCraft) {
        this.canCraft = canCraft;
    }

    public Boolean getCanWaterPlace() {
        Boolean canWaterPlace = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canWaterPlace") != null){
            canWaterPlace = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canWaterPlace");
        }
        return canWaterPlace;
    }

    public void setCanWaterPlace(Boolean canWaterPlace) {
        this.canWaterPlace = canWaterPlace;
    }

    public Boolean getCanBucketFill() {
        Boolean canBucketFill = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canBucketFill") != null){
            canBucketFill = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canBucketFill");
        }
        return canBucketFill;
    }

    public void setCanBuckteFill(Boolean canBuckteFill) {
        this.canBucketFill = canBuckteFill;
    }

    public Boolean getCanBeTarget() {
        Boolean canBucketFill = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canBucketFill") != null){
            canBucketFill = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canBucketFill");
        }
        return canBucketFill;
    }

    public void setCanBeTarget(Boolean canBeTarget) {
        this.canBeTarget = canBeTarget;
    }

    public Boolean getCanInteract() {
        Boolean canInteract = true;
        if(PlayerConfig.get("Gamer." + this.gameName + ".canInteract") != null){
            canInteract = (Boolean) PlayerConfig.get("Gamer." + this.gameName + ".canInteract");
        }
        return canInteract;
    }

    public void setCanInteract(Boolean canInteract) {
        this.canInteract = canInteract;
    }

    public void setCanBucketFill(Boolean canBucketFill) {
        this.canBucketFill = canBucketFill;
    }

    public void setPlayer(Player player){
        this.player = player;
    }
}
