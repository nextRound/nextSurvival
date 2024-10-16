package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.PlayerLocation;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

public class HomeCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String arg, String[] args) {
        Player player = (Player) sender;
        ServerConfig serverConfig = nextSurvival.serverConfig;

        if(command.getName().equalsIgnoreCase("home")) {
            if(args.length>=2) {
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" Syntax --> /home <set>")
                                .color(nextSurvival.error)));
                return false;
            }

            if(args.length==0) {
                if(!serverConfig.getHomeLocations().containsKey(player.getUniqueId())) {
                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" At first you have to set a home --> /home set")
                                    .color(nextSurvival.error)));
                    return false;
                }
                serverConfig.getHomeLocations().get(player.getUniqueId()).teleportPlayer(player);

                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text( " You are now at your home!")
                                .color(nextSurvival.primary)));

                return true;
            } else {
                if(args[0].equalsIgnoreCase("set")) {
                    serverConfig.addHomeLocation(player, new PlayerLocation(player));

                    FileManager.saveServerConfig(serverConfig);
                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" Your home is now set to this location!")
                                    .color(nextSurvival.primary)));
                    return true;
                }else{
                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" Syntax --> /home <set>")
                                    .color(nextSurvival.error)));
                    return false;
                }
            }
        } else if (command.getName().equalsIgnoreCase("back")) {
            if (args.length == 0) {
                if (serverConfig.getDeathLocations().containsKey(player.getUniqueId())) {
                    if(nextSurvival.instance.recentlyDied.contains(player)) {
                        serverConfig.getDeathLocations().get(player.getUniqueId()).teleportPlayer(player);

                        player.sendMessage(nextSurvival.PREFIX
                                .append(Component.text(" You are now at your death location!")
                                        .color(nextSurvival.primary)));
                    }else{
                        player.sendMessage(nextSurvival.PREFIX
                                .append(Component.text(" You ran out of time ")
                                        .color(nextSurvival.primary))
                                .append(Component.text(":/")
                                        .color(nextSurvival.highlight_yellow)
                                        .decoration(TextDecoration.BOLD, true))
                                .append(Component.text(" There is no going back now. Sorry ")
                                        .color(nextSurvival.primary))
                                .append(Component.text(",_,")
                                        .color(nextSurvival.highlight_yellow)
                                        .decoration(TextDecoration.BOLD, true)));
                    }
                } else {
                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" You have no death location saved by the server!")
                                    .color(nextSurvival.error)));
                }
                return true;
            }
        }else if (command.getName().equalsIgnoreCase("sethome")) {
            serverConfig.addHomeLocation(player, new PlayerLocation(player));

            FileManager.saveServerConfig(serverConfig);

            player.sendMessage(nextSurvival.PREFIX
                    .append(Component.text(" Your home is now set to this location!")
                            .color(nextSurvival.primary)));
            return true;
        }
        return false;
    }
}
