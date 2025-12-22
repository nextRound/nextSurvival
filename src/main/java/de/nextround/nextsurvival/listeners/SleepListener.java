package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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

public class SleepListener implements Listener {

    public SleepListener() {
        Bukkit.getPluginManager().registerEvents(this, nextSurvival.instance);
    }

    /*
     * Checks how many players are sleeping and creates a ratio compared to all players on the server
     * checks if the night should be skipped
     */
    @EventHandler
    public void sleepDeepEvent(PlayerDeepSleepEvent event) {
        double count = Bukkit.getOnlinePlayers().stream().filter(LivingEntity::isSleeping).count();
        double ratio = count / Bukkit.getOnlinePlayers().size();

        Bukkit.getServer().sendMessage(nextSurvival.PREFIX
                .append(Component.text(" " + event.getPlayer().getName())
                        .color(nextSurvival.highlight_blue))
                .append(Component.text(" is now sleeping ")
                        .color(nextSurvival.primary))
                .append(Component.text("(")
                        .color(nextSurvival.highlight_secondary))
                .append(Component.text(Math.floor(ratio * 100) + "%")
                        .color(nextSurvival.white)
                        .decoration(TextDecoration.BOLD, true))
                .append(Component.text(")")
                        .color(nextSurvival.highlight_secondary)));

        checkToSkipTheNight(event.getPlayer().getWorld(), new ArrayList<>(Bukkit.getOnlinePlayers()));
    }

    /*
     * Checks if enough players are sleeping and skipping the night
     */
    public static void checkToSkipTheNight(World world, ArrayList<Player> players) {
        double count = Bukkit.getOnlinePlayers().stream().filter(LivingEntity::isSleeping).count();
        double ratio = count / players.size();

        if (ratio >= 0.3) {
            TextComponent night = Component.text(" " + Math.floor(ratio * 100) + "%")
                    .color(nextSurvival.white)
                    .decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" are sleeping. Skipping the night...")
                            .color(nextSurvival.primary)
                            .decoration(TextDecoration.BOLD, false));
            TextComponent thunderstorm = Component.text(" " + Math.floor(ratio * 100) + "%")
                    .color(nextSurvival.white)
                    .decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" are sleeping. Skipping the thunderstorm...")
                            .color(nextSurvival.primary)
                            .decoration(TextDecoration.BOLD, false));

            TextComponent message = world.isClearWeather() ? night : thunderstorm;

            Bukkit.getServer().sendMessage(nextSurvival.PREFIX.append(message));

            Bukkit.getScheduler().runTaskLater(nextSurvival.instance, () -> forward(world), 40);
        }
    }

    /*
     * Adds +80 to the given world time as long as the time is between 12516 and 23900
     */
    private static void forward(World world) {
        world.setTime(world.getTime() + 80);

        if (world.getTime() > 12516 && world.getTime() < 23900)
            nextSurvival.instance.getServer().getScheduler().runTaskLater(nextSurvival.instance, () -> forward(world), 1);
    }
}
