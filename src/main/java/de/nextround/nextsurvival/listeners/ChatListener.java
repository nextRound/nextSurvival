package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
     * Displays a chat message depending on the prefix of the player (if one exists) and
     * checks if the player is part of password array that was created in ConnectionListener>onJoinListener
     * if that's the case it checks if the password the player typed is equal to the config.json entry
     * if the player got it right the password entry of the player is set to true
     */
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            ServerConfig serverConfig = ServerConfig.getServerConfig();
            if (!nextSurvival.instance.password.contains(source)) {
                String textMessage = ((TextComponent)message).content().replace(ServerConfig.getServerConfig().getPassword(), "********");

                if (serverConfig.getPrefixes().containsKey(source.getUniqueId())) {
                    return LegacyComponentSerializer.legacySection().deserialize(serverConfig.getPrefixes().get(source.getUniqueId()).replace("&", "§") + " §5" + source.getName() + " §8» §r" + textMessage);
                } else {
                    return LegacyComponentSerializer.legacySection().deserialize("§5" + source.getName() + " §8» §r" + textMessage);
                }
            }else{
                if(((TextComponent) message).content().equals(ServerConfig.getServerConfig().getPassword())) {
                    event.setCancelled(true);

                    nextSurvival.instance.password.remove(source);
                    serverConfig.addPasswordChecker(source.getUniqueId(), true);

                    Bukkit.getScheduler().runTask(nextSurvival.instance ,()-> {
                        source.setGameMode(GameMode.SURVIVAL);
                    });

                    source.sendMessage(nextSurvival.PREFIX + " §3You got it right! §d§l§k::: §r§bYaaay Wooop Wooop §d§l§k:::");
                    System.out.println(nextSurvival.PREFIX + " " + source.getName() + " is now a member of the server!");
                    FileManager.updateDefaultServerConfigFile(serverConfig);
                }
            }

            return Component.empty();
        });
    }

}
