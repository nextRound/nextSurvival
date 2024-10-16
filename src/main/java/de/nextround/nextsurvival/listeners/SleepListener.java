package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

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
     * Checks if player enters a bed
     * Checks how many players are sleeping and creates a ratio compared to all players on the server
     * Checks if that ratio is >= 0.3 (30%) and forwards the time (forward(World world))
     */
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            double count = Bukkit.getOnlinePlayers().stream().filter(LivingEntity::isSleeping).count() + 1;
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

            if (ratio >= 0.3) {
                event.getBed().getWorld().setClearWeatherDuration(Integer.MAX_VALUE);

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

                TextComponent message = event.getBed().getWorld().isClearWeather() ? night : thunderstorm;

                Bukkit.getServer().sendMessage(nextSurvival.PREFIX.append(message));

                Bukkit.getScheduler().runTaskLater(nextSurvival.instance, () -> forward(event.getBed().getWorld()), 40);
            }
        }
    }

    /*
     * Adds +80 to the given world time as long as the time is between 12516 and 23900
     */
    private void forward(World world) {
        world.setTime(world.getTime() + 80);

        if (world.getTime() > 12516 && world.getTime() < 23900)
            nextSurvival.instance.getServer().getScheduler().runTaskLater(nextSurvival.instance, () -> forward(world), 1);
    }
}
