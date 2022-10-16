package de.nextround.nextsurvival;

import de.nextround.nextsurvival.commands.HomeCommand;
import de.nextround.nextsurvival.commands.PrefixCommand;
import de.nextround.nextsurvival.listeners.*;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
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
    public static final String PREFIX = "§8[§3nextSurvival§8]";

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
        new Recipes(this).registerRecipes();

        FileManager.createDefaultServerConfigFile(new ServerConfig("bamboowagon"));

        /* Console enable message */
        Bukkit.broadcastMessage(PREFIX + " §cPlease rejoin to load all features of nextSurvival!");

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
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("sethome").setExecutor(new HomeCommand(this));
        getCommand("back").setExecutor(new HomeCommand(this));
        getCommand("prefix").setExecutor(new PrefixCommand());
    }

    /*
     * Function to register all listeners in de.nextround.nextsurvival.listeners
     */
    private void registerListeners() {
        new ConnectionListeners(this);
        new ChatListener(this);
        new MoveListener(this);
        new SleepListener(this);
        new GriefListener(this);
    }
}
