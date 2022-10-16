package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.listeners.utilities.FileManager;
import de.nextround.nextsurvival.listeners.utilities.PlayerLocation;
import de.nextround.nextsurvival.listeners.utilities.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

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

public class MoveListener implements Listener {

    public final nextSurvival instance;

    public MoveListener(nextSurvival instance) {
        this.instance = instance;
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    /*
     * A PlayerMoveEvent that detects the name of the boots the player is wearing
     * Spawns, depending on the name, different particles
     */
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(player.getInventory().getBoots() != null) {
            if (Objects.requireNonNull(player.getInventory().getBoots().getItemMeta()).getDisplayName().equals("Love")) {

                player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Flame")) {

                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Smoke")) {

                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Lava")) {

                player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Ender")) {

                player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Water")) {

                player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation(), 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Firework")) {

                player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 1);

            } else if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals("Cloud")) {

                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 1);
            }
        }
    }

    /*
     * Disables the PlayerMoveEvent for every player where the password is set to "false" in the config.json
     */
    @EventHandler
    public void onDisableMoveEvent(PlayerMoveEvent event) {
        event.setCancelled(instance.password.contains(event.getPlayer()));
    }

    /*
     * Stops BAT´s from spawning
     * Reduces the spawn-rate of PILLAGERS by 20%
     */
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.BAT) {
            event.setCancelled(true);
        } else if (event.getEntityType() == EntityType.PILLAGER) {
            if (Math.random() < 0.8) {
                event.setCancelled(true);
            }
        }
    }

    /*
     * Saves the death-location of the player in the config.json
     * Starts a timer (60s) which determines how much time the player has to execute the command /back
     */
    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        instance.recentlyDied.add(player);

        ServerConfig serverConfig = ServerConfig.getServerConfig();
        serverConfig.addDeathLocation(player, new PlayerLocation(player));
        FileManager.updateDefaultServerConfigFile(serverConfig);

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            instance.recentlyDied.remove(player);
            player.sendMessage(nextSurvival.PREFIX + " §9Death location was disabled after §d§l60 §r§9seconds!");
        },1200);
    }

    /**
     * Interact event for the Portable Crafting table. Cancels the event and opens the workbench inventory.
     */
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if(item != null && item.getItemMeta() != null) {
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§2Portable Workbench")) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.getPlayer().openWorkbench(null, true);
                    event.setCancelled(true);
                }
            }
        }
    }
}
