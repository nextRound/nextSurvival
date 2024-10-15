package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
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
 *    Author: nextRound (Nikki S.)
 *    Copyright (C) Nikki S.
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
    public void onSleep(PlayerBedEnterEvent e) {
        if (e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            double count = Bukkit.getOnlinePlayers().stream().filter(LivingEntity::isSleeping).count() + 1;
            double ratio = count / Bukkit.getOnlinePlayers().size();
            Bukkit.broadcastMessage(nextSurvival.PREFIX + " §5" + e.getPlayer().getDisplayName() +
                    " §3is now sleeping §7(§f§l" + Math.floor(ratio * 100) + "%§7)");
            if (ratio >= 0.3) {
                e.getBed().getWorld().setClearWeatherDuration(Integer.MAX_VALUE);
                String message = e.getBed().getWorld().isClearWeather() ? " §f§l"+Math.floor(ratio * 100)+ "% §3are sleeping. Skipping the night..." :
                        " §f§l"+Math.floor(ratio * 100)+"% §3are sleeping. Skipping the thunderstorm...";
                Bukkit.broadcastMessage(nextSurvival.PREFIX + ChatColor.BLUE + message);
                Bukkit.getScheduler().runTaskLater(nextSurvival.instance, () -> forward(e.getBed().getWorld()), 40);
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
