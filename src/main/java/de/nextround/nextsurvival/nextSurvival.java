package de.nextround.nextsurvival;

import de.nextround.nextsurvival.commands.HomeCommand;
import de.nextround.nextsurvival.commands.PrefixCommand;
import de.nextround.nextsurvival.listeners.*;
import de.nextround.nextsurvival.listeners.utilities.FileManager;
import de.nextround.nextsurvival.listeners.utilities.ServerConfig;
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
        registerRecipe();

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

    public void registerRecipe() {
        addSlabRecipe("oak_slab_plank", Material.OAK_PLANKS, Material.OAK_SLAB);
        addSlabRecipe("spruce_slab_plank", Material.SPRUCE_PLANKS, Material.SPRUCE_SLAB);
        addSlabRecipe("birch_slab_plank", Material.BIRCH_PLANKS, Material.BIRCH_SLAB);
        addSlabRecipe("jungle_slab_plank", Material.JUNGLE_PLANKS, Material.JUNGLE_SLAB);
        addSlabRecipe("acacia_slab_plank", Material.ACACIA_PLANKS, Material.ACACIA_SLAB);
        addSlabRecipe("dark_oak_slab_plank", Material.DARK_OAK_PLANKS, Material.DARK_OAK_SLAB);
        addSlabRecipe("crimson_slab_plank", Material.CRIMSON_PLANKS, Material.CRIMSON_SLAB);
        addSlabRecipe("warped_slab_plank", Material.WARPED_PLANKS, Material.WARPED_SLAB);

        addSlabRecipe("stone_slab_block", Material.STONE, Material.STONE_SLAB);
        addSlabRecipe("smooth_stone_slab_block", Material.SMOOTH_STONE, Material.SMOOTH_STONE_SLAB);
        addSlabRecipe("sandstone_slab_block", Material.SANDSTONE, Material.SANDSTONE_SLAB);
        addSlabRecipe("cut_sandstone_slab_block", Material.CUT_SANDSTONE, Material.CUT_SANDSTONE_SLAB);
        addSlabRecipe("cobblestone_slab_block", Material.COBBLESTONE, Material.COBBLESTONE_SLAB);
        addSlabRecipe("brick_slab_block", Material.BRICKS, Material.BRICK_SLAB);
        addSlabRecipe("stone_bricks_slab_block", Material.STONE_BRICKS, Material.STONE_BRICK_SLAB);
        addSlabRecipe("nether_slab_block", Material.NETHER_BRICKS, Material.NETHER_BRICK_SLAB);
        addSlabRecipe("quartz_slab_block", Material.QUARTZ_BLOCK, Material.QUARTZ_SLAB);

        addPortableWorkbenchRecipe("portable_workbench_oak", Material.OAK_WOOD);
        addPortableWorkbenchRecipe("portable_workbench_spruce",Material.SPRUCE_WOOD);
        addPortableWorkbenchRecipe("portable_workbench_birch",Material.BIRCH_WOOD);
        addPortableWorkbenchRecipe("portable_workbench_jungle",Material.JUNGLE_WOOD);
        addPortableWorkbenchRecipe("portable_workbench_acacia",Material.ACACIA_WOOD);
        addPortableWorkbenchRecipe("portable_workbench_dark_oak",Material.DARK_OAK_WOOD);
    }

    public void addSlabRecipe(String key, Material result, Material ingredient) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, key), new ItemStack(result));
        recipe.shape("O", "O");
        recipe.setIngredient('O', ingredient);
        Bukkit.addRecipe(recipe);
    }

    public void addPortableWorkbenchRecipe(String key, Material wood) {
        ItemStack craftItem = new ItemStack(Material.CRAFTING_TABLE, 1);
        ItemMeta itemMeta = craftItem.getItemMeta();
        if(itemMeta != null) {
            itemMeta.setDisplayName("§2Portable Workbench");
            itemMeta.addEnchant(Enchantment.LUCK, 100, true);
            craftItem.setItemMeta(itemMeta);
        }

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, key), craftItem);
        recipe.shape("OOO", "OSO", "OOO");
        recipe.setIngredient('O', wood);
        recipe.setIngredient('S', Material.DIAMOND);
        Bukkit.addRecipe(recipe);
    }
}
