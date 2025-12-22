package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

public class ConnectionListeners implements Listener {

    public ConnectionListeners() {
        Bukkit.getPluginManager().registerEvents(this, nextSurvival.instance);
    }

    /*
     * Displays a beautiful connect message depending on the prefix (if one exists)
     * Checks if the password entry of the player if true or false
     * if the player UUID doesn't exist it creates an entry with the value false in the config.json
     */
    @EventHandler
    public void onJoinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ServerConfig serverConfig = nextSurvival.serverConfig;

        joinMessageAndTabList(event, serverConfig, player);
        passwordCheckMessage(serverConfig, player);
        setTabHeaderFooter(player);
    }

    private static void joinMessageAndTabList(PlayerJoinEvent event, ServerConfig serverConfig, Player player) {
        MoveListener.updateDeathCounter(serverConfig, player, player.getStatistic(Statistic.DEATHS));

        if(serverConfig.getPrefixes().containsKey(player.getUniqueId())) {
            event.joinMessage(Component.text("[")
                    .color(nextSurvival.highlight_secondary)
                    .append(Component.text("+")
                            .color(TextColor.color(0xFF1A)))
                    .append(Component.text("] ")
                            .color(nextSurvival.highlight_secondary))
                    .append(Component.text(serverConfig.getPrefixes().get(player.getUniqueId()).replace("&","§")))
                    .append(Component.text(" " + player.getName())
                            .color(nextSurvival.highlight_primary)));
        }else{
            event.joinMessage(Component.text("[")
                    .color(nextSurvival.highlight_secondary)
                    .append(Component.text("+")
                            .color(TextColor.color(0xFF1A)))
                    .append(Component.text("] ")
                            .color(nextSurvival.highlight_secondary))
                    .append(Component.text(player.getName())
                            .color(nextSurvival.highlight_primary)));
        }
    }

    private static void passwordCheckMessage(ServerConfig serverConfig, Player player) {
        if(!serverConfig.getPasswordChecker().containsKey(player.getUniqueId()) || !serverConfig.getPasswordChecker().get(player.getUniqueId())) {
            if(!serverConfig.getPasswordChecker().containsKey(player.getUniqueId()))
                serverConfig.addPasswordChecker(player.getUniqueId(), false);

            nextSurvival.instance.password.add(player);
            player.setGameMode(GameMode.ADVENTURE);

            player.sendMessage(nextSurvival.PREFIX
                    .append(Component.text(" Please use the command ")
                            .color(nextSurvival.error))
                    .append(Component.text("/password [password-string]")
                            .color(nextSurvival.highlight_yellow)
                            .decoration(TextDecoration.BOLD, true))
                    .append(Component.text(" to provide the server password!")
                            .color(nextSurvival.error)));
        }
    }

    private static void setTabHeaderFooter(Player player) {
        long days = player.getWorld().getGameTime()/24000;

        Component dayOrNight = LocalTime.now().getHour() >= 20 || LocalTime.now().getHour() <= 8 ?
                Component.text("night")
                .color(nextSurvival.highlight_blue) :
                Component.text("day")
                .color(nextSurvival.highlight_yellow);

        Component header = Component.text(days)
                .color(nextSurvival.highlight_primary)
                .decoration(TextDecoration.BOLD, true)
                .append(Component.text(" Days Passed")
                        .color(nextSurvival.primary)
                        .decoration(TextDecoration.BOLD, false));
        Component footer = Component.text("Have a great ")
                .color(nextSurvival.primary)
                .append(dayOrNight)
                .append(Component.text(" :3")
                        .color(nextSurvival.highlight_primary)
                        .decoration(TextDecoration.BOLD, true));
        player.sendPlayerListHeaderAndFooter(header, footer);
    }

    /*
     * Displays a beautiful disconnect message depending on the prefix (if one exists)
     */
    @EventHandler
    public void onDisconnectListener(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ServerConfig serverConfig = nextSurvival.serverConfig;

        if(serverConfig.getPrefixes().containsKey(player.getUniqueId())) {
            event.quitMessage(Component.text("[")
                    .color(nextSurvival.highlight_secondary)
                    .append(Component.text("-")
                            .color(TextColor.color(0xFF2D4B)))
                    .append(Component.text("] ")
                            .color(nextSurvival.highlight_secondary))
                    .append(Component.text(serverConfig.getPrefixes().get(player.getUniqueId()).replace("&","§")))
                    .append(Component.text(" " + player.getName())
                            .color(nextSurvival.highlight_primary)));
        } else {
            event.quitMessage(Component.text("[")
                    .color(nextSurvival.highlight_secondary)
                    .append(Component.text("-")
                            .color(TextColor.color(0xFF2D4B)))
                    .append(Component.text("] ")
                            .color(nextSurvival.highlight_secondary))
                    .append(Component.text(player.getName())
                            .color(nextSurvival.highlight_primary)));
        }

        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<Player> sleepingPlayers = new ArrayList<>(players.stream().filter(LivingEntity::isSleeping).toList());

        players.remove(player);

        if(!sleepingPlayers.isEmpty()) {
            SleepListener.checkToSkipTheNight(sleepingPlayers.getFirst().getWorld(), players);
        }
    }
}