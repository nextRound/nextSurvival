package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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

            String textMessage = ((TextComponent)message).content().replace(serverConfig.getPassword(), "********");

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
            }else{
                if(((TextComponent) message).content().equals(serverConfig.getPassword())) {
                    event.setCancelled(true);

                    nextSurvival.instance.password.remove(source);
                    serverConfig.addPasswordChecker(source.getUniqueId(), true);

                    Bukkit.getScheduler().runTask(nextSurvival.instance ,()-> {
                        source.setGameMode(GameMode.SURVIVAL);
                    });

                    source.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" You got it right! ")
                                    .color(nextSurvival.primary))
                            .append(Component.text(":::")
                                    .color(nextSurvival.highlight_yellow)
                                    .decoration(TextDecoration.OBFUSCATED, true)
                                    .decoration(TextDecoration.BOLD, true))
                            .append(Component.text(" Yaaay Wooop Wooop ")
                                    .color(nextSurvival.highlight_blue)
                                    .decoration(TextDecoration.BOLD, true))
                            .append(Component.text(":::")
                                    .color(nextSurvival.highlight_yellow)
                                    .decoration(TextDecoration.OBFUSCATED, true)
                                    .decoration(TextDecoration.BOLD, true)));

                    System.out.println(nextSurvival.PREFIX + " " + source.getName() + " is now a member of the server!");

                    FileManager.saveServerConfigFile(serverConfig);
                }
            }

            return Component.empty();
        });
    }
}
