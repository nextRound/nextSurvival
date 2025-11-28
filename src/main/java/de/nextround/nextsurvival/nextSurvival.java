package de.nextround.nextsurvival;

import de.nextround.nextsurvival.commands.HomeCommand;
import de.nextround.nextsurvival.commands.HorseSpeedCommand;
import de.nextround.nextsurvival.commands.PrefixCommand;
import de.nextround.nextsurvival.listeners.*;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 *
 *
 *    █▀▀▄ █▀▀ █░█ ▀▀█▀▀ ▒█▀▀▀█ █░░█ █▀▀█ ▀█░█▀ ░▀░ ▀█░█▀ █▀▀█ █░░
 *    █░░█ █▀▀ ▄▀▄ ░░█░░ ░▀▀▀▄▄ █░░█ █▄▄▀ ░█▄█░ ▀█▀ ░█▄█░ █▄▄█ █░░
 *    ▀░░▀ ▀▀▀ ▀░▀ ░░▀░░ ▒█▄▄▄█ ░▀▀▀ ▀░▀▀ ░░▀░░ ▀▀▀ ░░▀░░ ▀░░▀ ▀▀▀
 *
 *    Project: nextSurvival
 *    Author: Nicole Scheitler (nextRound)
 *    Copyright - GNU GPLv3 (C) Nicole Scheitler
 *
 *
 */

public class nextSurvival extends JavaPlugin {

    // Classes
    public static nextSurvival instance;

    // Lists/Hashmaps
    public final ArrayList<Player> password = new ArrayList<>();
    public final ArrayList<Player> recentlyDied = new ArrayList<>();

    // Server Config
    public static ServerConfig serverConfig;

    // Colors
    public static final TextColor primary = TextColor.color(0x90FC96);
    public static final TextColor secondary = TextColor.color(0x9BBD9D);
    public static final TextColor white = TextColor.color(0xffffff);
    public static final TextColor error = TextColor.color(0xE94153);
    public static final TextColor highlight_primary = TextColor.color(0xd63777);
    public static final TextColor highlight_secondary = TextColor.color(0x402437);
    public static final TextColor highlight_blue = TextColor.color(0x7DA1FF);
    public static final TextColor highlight_yellow = TextColor.color(0xFFD670);

    // Prefix
    public static final TextComponent PREFIX = Component.text("[")
            .color(highlight_secondary)
            .append(Component.text("nextSurvival")
                    .color(highlight_primary)
                    .decoration(TextDecoration.BOLD, true))
            .append(Component.text("]")
                    .color(highlight_secondary)
            );

    @Override
    public void onEnable() {
        try {
            serverConfig = new File("plugins/nextSurvival/", "config.json").exists() ?
                    FileManager.getServerConfig() :
                    FileManager.createDefaultServerConfigFile();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §cConfig creation/loading failed!");
            Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §cConfig will only be cached and is " +
                    "temporary until the next server restart!");
            Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §cPassword is: §ebamboowagon");
            serverConfig = new ServerConfig("bamboowagon", "1.0");
        }

        instance = this;
        registerCommands();
        registerListeners();
        Recipes.registerRecipes();

        /* Console enable message */
        Bukkit.getServer().sendMessage(PREFIX.append(Component.text(" Please rejoin to load all features of nextSurvival!").color(error)));

        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3The plugin is now §aEnabled");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3Coded by: §enextRound");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3Version: §e" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextColors§8] §3Thank you for downloading my plugin!");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §c(c) Copyright Nicole Scheitler");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3The plugin is now §cDisabled");
    }

    /*
     * Function to register all commands in de.nextround.nextsurvival.commands
     */
    private void registerCommands() {
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new HomeCommand());
        getCommand("back").setExecutor(new HomeCommand());
        getCommand("prefix").setExecutor(new PrefixCommand());
        getCommand("getHorseSpeed").setExecutor(new HorseSpeedCommand());
    }

    /*
     * Function to register all listeners in de.nextround.nextsurvival.listeners
     */
    private void registerListeners() {
        new ConnectionListeners();
        new ChatListener();
        new MoveListener();
        new SleepListener();
        new GriefListener();
    }
}
