package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

public class ChatListener implements Listener {

    public ChatListener() {
        Bukkit.getPluginManager().registerEvents(this, nextSurvival.instance);
    }

    /*
     * Displays a chat message depending on the prefix of the player (if one exists)
     */
    @EventHandler
    public void onAChatListener(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);

        ServerConfig serverConfig = ServerConfig.getServerConfig();

        if (!nextSurvival.instance.password.contains(player)) {
            if (serverConfig.getPrefixes().containsKey(player.getUniqueId())) {
                Bukkit.broadcastMessage(serverConfig.getPrefixes().get(player.getUniqueId()).replace("&", "§") + " §5" + player.getName() + " §8» §r" + event.getMessage());
            } else {
                Bukkit.broadcastMessage("§5" + player.getName() + " §8» §r" + event.getMessage());
            }
        }
    }

    /*
     * Checks if the player is part of password array that was created in ConnectionListener>onJoinListener
     * if that's the case it checks if the password the player typed is equal to the config.json entry
     * if the player got it right the password entry of the player is set to true
     */
    @EventHandler
    public void onChatListener(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        ServerConfig serverConfig = ServerConfig.getServerConfig();

        if(nextSurvival.instance.password.contains(player)) {
            if(event.getMessage().equals(ServerConfig.getServerConfig().getPassword())) {
                event.setCancelled(true);
                nextSurvival.instance.password.remove(player);
                serverConfig.addPasswordChecker(player.getUniqueId(), true);

                Bukkit.getScheduler().runTask(nextSurvival.instance ,()-> {
                    player.setGameMode(GameMode.SURVIVAL);
                });

                player.sendMessage(nextSurvival.PREFIX + " §9You got it right! §d§l§k::: §r§bYaaay Wooop Wooop §d§l§k:::");
                System.out.println(nextSurvival.PREFIX + " " + player.getName() + " is now a member of the server!");
                FileManager.updateDefaultServerConfigFile(serverConfig);
            }
        }
    }
}
