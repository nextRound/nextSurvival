package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.ServerConfig;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
            ServerConfig serverConfig = nextSurvival.serverConfig;

            String textMessage = ((TextComponent) message).content();

            if (!nextSurvival.instance.password.contains(source)) {
                if (serverConfig.getPrefixes().containsKey(source.getUniqueId())) {
                    String playerPrefix = serverConfig.getPrefixes().get(source.getUniqueId()).replace("&", "§");

                    return Component.text(playerPrefix)
                            .append(Component.text(" " + source.getName())
                                    .color(nextSurvival.highlight_primary))
                            .append(Component.text(" » ")
                                    .color(nextSurvival.highlight_secondary))
                            .append(Component.text(textMessage).color(nextSurvival.white));
                } else {
                    return Component.text(source.getName())
                                    .color(nextSurvival.highlight_primary)
                            .append(Component.text(" » ")
                                    .color(nextSurvival.highlight_secondary))
                            .append(Component.text(textMessage).color(nextSurvival.white));
                }
            }

            return Component.empty();
        });
    }
}
