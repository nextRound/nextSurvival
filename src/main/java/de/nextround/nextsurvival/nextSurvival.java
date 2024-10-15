package de.nextround.nextsurvival;

import de.nextround.nextsurvival.commands.HomeCommand;
import de.nextround.nextsurvival.commands.PrefixCommand;
import de.nextround.nextsurvival.listeners.*;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/*
 *
 *
 *    █▀▀▄ █▀▀ █░█ ▀▀█▀▀ ▒█▀▀▀█ █░░█ █▀▀█ ▀█░█▀ ░▀░ ▀█░█▀ █▀▀█ █░░
 *    █░░█ █▀▀ ▄▀▄ ░░█░░ ░▀▀▀▄▄ █░░█ █▄▄▀ ░█▄█░ ▀█▀ ░█▄█░ █▄▄█ █░░
 *    ▀░░▀ ▀▀▀ ▀░▀ ░░▀░░ ▒█▄▄▄█ ░▀▀▀ ▀░▀▀ ░░▀░░ ▀▀▀ ░░▀░░ ▀░░▀ ▀▀▀
 *
 *    Project: nextSurvival
 *    Author: nextRound (Nikki S.)
 *    Copyright (C) Nikki S.
 *
 *
 */

public class nextSurvival extends JavaPlugin {

    // Prefix
    public static final String PREFIX = "§8[§dnextSurvival§8]";

    // Classes
    public static nextSurvival instance;

    // Lists/Hashmaps
    public final ArrayList<Player> password = new ArrayList<>();
    public final ArrayList<Player> recentlyDied = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        Recipes.registerRecipes();

        FileManager.createDefaultServerConfigFile(new ServerConfig("bamboowagon"));

        /* Console enable message */
        Bukkit.getServer().sendMessage(Component.text(PREFIX + " §cPlease rejoin to load all features of nextSurvival!"));

        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3The plugin is now §aEnabled");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3Coded by: §enextRound");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3Version: §e" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §c(c) Copyright Nicole S.");
    }

    /*
     * Function to register all commands in de.nextround.nextsurvival.commands
     */
    private void registerCommands() {
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new HomeCommand());
        getCommand("back").setExecutor(new HomeCommand());
        getCommand("prefix").setExecutor(new PrefixCommand());
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
