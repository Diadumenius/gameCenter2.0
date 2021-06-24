package de.diadumenius.gamecenter.util;

import de.diadumenius.gamecenter.functions.PluginFunctions;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

// this class was created by Diadumenius
public enum GameState {

    MAIN_LOBBY("Hauptlobby"),
    BEDWARS_LOBBY("BedWars-Lobby"),
    SURVIVAL("Survival"),
    BEDWARS_INGAME("BedWars-Ingame"),
    BEDWARS_SPEC("BedWars-Zuschauer"),
    BUILDER("Builder");

    String translation;

    private GameState(String translation){
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public TextComponent getTextComponent(){
        TextComponent text = new TextComponent("§a" +this.getTranslation());
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7<<§6§n" + this.getTranslation() + "§7>>")
                .append("\n")
                .append("\n").append(ChatColor.DARK_GREEN + "  • Spieler online: " + "§a§l" + PluginFunctions.getGameStatePlayers(this).size() + "  ")
                .append("\n")
                .create()));
        return text;
    }
}
