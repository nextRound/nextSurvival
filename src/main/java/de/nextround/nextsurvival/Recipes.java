package de.nextround.nextsurvival;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

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

public class Recipes {

    public static void registerRecipes() {
        addSlabRecipe("oak_slab_plank", Material.OAK_PLANKS, Material.OAK_SLAB);
        addSlabRecipe("spruce_slab_plank", Material.SPRUCE_PLANKS, Material.SPRUCE_SLAB);
        addSlabRecipe("birch_slab_plank", Material.BIRCH_PLANKS, Material.BIRCH_SLAB);
        addSlabRecipe("jungle_slab_plank", Material.JUNGLE_PLANKS, Material.JUNGLE_SLAB);
        addSlabRecipe("acacia_slab_plank", Material.ACACIA_PLANKS, Material.ACACIA_SLAB);
        addSlabRecipe("dark_oak_slab_plank", Material.DARK_OAK_PLANKS, Material.DARK_OAK_SLAB);
        addSlabRecipe("crimson_slab_plank", Material.CRIMSON_PLANKS, Material.CRIMSON_SLAB);
        addSlabRecipe("warped_slab_plank", Material.WARPED_PLANKS, Material.WARPED_SLAB);
        addSlabRecipe("cherry_slab_plank", Material.CHERRY_PLANKS, Material.CHERRY_SLAB);
        addSlabRecipe("mangrove_slab_plank", Material.MANGROVE_PLANKS, Material.MANGROVE_SLAB);

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

    /**
     * Adds new slap recipe to the spigot server with the specific pattern.
     * @param key name of the recipe
     */
    public static void addSlabRecipe(String key, Material result, Material ingredient) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(nextSurvival.instance, key), new ItemStack(result));
        recipe.shape("O", "O");
        recipe.setIngredient('O', ingredient);
        Bukkit.addRecipe(recipe);
    }

    /**
     * Adds new portable workbench recipe to the spigot server with the specific pattern.
     * @param key name of the recipe
     */
    public static void addPortableWorkbenchRecipe(String key, Material wood) {
        ItemStack craftItem = new ItemStack(Material.CRAFTING_TABLE, 1);
        ItemMeta itemMeta = craftItem.getItemMeta();
        if(itemMeta != null) {
            itemMeta.displayName(Component.text("Portable Workbench", NamedTextColor.AQUA));
            itemMeta.setCustomModelData(1);
            itemMeta.addEnchant(Enchantment.FORTUNE, 100, true);
            craftItem.setItemMeta(itemMeta);
        }

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(nextSurvival.instance, key), craftItem);
        recipe.shape("OOO", "OSO", "OOO");
        recipe.setIngredient('O', wood);
        recipe.setIngredient('S', Material.DIAMOND);
        Bukkit.addRecipe(recipe);
    }
}
