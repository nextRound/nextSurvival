package de.nextround.nextsurvival.listeners;

import com.destroystokyo.paper.ParticleBuilder;
import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.PlayerLocation;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
 *    Author: Nicole Scheitler (nextRound)
 *    Copyright - GNU GPLv3 (C) Nicole Scheitler
 *
 *
 */

public class MoveListener implements Listener {

    public MoveListener() {
        Bukkit.getPluginManager().registerEvents(this, nextSurvival.instance);
    }

    /*
     * A PlayerMoveEvent that detects the name of the boots the player is wearing
     * Spawns, depending on the name, different particles
     */
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta() != null
                && player.getInventory().getBoots().getItemMeta().hasDisplayName()) {
            String displayName = ((TextComponent) player.getInventory().getBoots().getItemMeta().displayName()).content();

            if (displayName.equals("Love")) {

                player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), 1);

            } else if (displayName.equals("Flame")) {

                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 1);

            } else if (displayName.equals("Smoke")) {

                player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 1);

            } else if (displayName.equals("Lava")) {

                player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 1);

            } else if (displayName.equals("Ender")) {

                player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);

            } else if (displayName.equals("Water")) {

                player.getWorld().spawnParticle(Particle.SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SPLASH, player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.SPLASH, player.getLocation(), 1);

            } else if (displayName.equals("Firework")) {

                player.getWorld().spawnParticle(Particle.FIREWORK, player.getLocation(), 1);

            } else if (displayName.equals("Cloud")) {

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
        event.setCancelled(nextSurvival.instance.password.contains(event.getPlayer()));
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

        nextSurvival.instance.recentlyDied.add(player);

        ServerConfig serverConfig = nextSurvival.serverConfig;
        serverConfig.addDeathLocation(player, new PlayerLocation(player));
        FileManager.saveServerConfigFile(serverConfig);

        Bukkit.getScheduler().runTaskLater(nextSurvival.instance, () -> {
            nextSurvival.instance.recentlyDied.remove(player);
            player.sendMessage(nextSurvival.PREFIX
                    .append(Component.text(" Death location was disabled after ")
                            .color(nextSurvival.primary))
                    .append(Component.text("60")
                            .color(nextSurvival.highlight_yellow)
                            .decoration(TextDecoration.BOLD, true))
                    .append(Component.text(" seconds!")
                            .color(nextSurvival.primary)));
        },1200);
    }

    /**
     * Interact event for the Portable Crafting table. Cancels the event and opens the workbench inventory.
     */
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if(item != null && item.getItemMeta() != null && item.getItemMeta().hasDisplayName()) {
            if(item.getItemMeta().getCustomModelData() == 1) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.getPlayer().openWorkbench(null, true);
                    event.setCancelled(true);
                }
            }
        }
    }
}
