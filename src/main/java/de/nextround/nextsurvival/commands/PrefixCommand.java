package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
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

public class PrefixCommand implements CommandExecutor {

    /*
     * Creates a /prefix [name] [prefix] command
     */
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String arg, String[] args) {
        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("prefix")) {
            if(args.length>=3) {
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" Syntax --> /prefix [name] [prefix]")
                                .color(nextSurvival.error)));
                return false;
            }
            if(args.length==0) {
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" Syntax --> /prefix [name] [prefix]")
                                .color(nextSurvival.error)));
                return false;
            }else if(args.length==1) {
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" Syntax --> /prefix [name] [prefix]")
                                .color(nextSurvival.error)));
                return false;
            }else {
                ServerConfig serverConfig = nextSurvival.serverConfig;

                if(Bukkit.getServer().getPlayer(args[0]) != null) {
                    serverConfig.addPrefixes(Bukkit.getServer().getPlayer(args[0]).getUniqueId(), "&f" + args[1]);
                }else{
                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" Player is not on the server!")
                                    .color(nextSurvival.error)));
                    return true;
                }

                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" You have set the prefix '")
                                .color(nextSurvival.primary))
                        .append(Component.text("§f" + args[1].replace("&", "§")))
                        .append(Component.text("' for ")
                                .color(nextSurvival.primary))
                        .append(Component.text(args[0])
                                .color(nextSurvival.highlight_blue)
                                .decoration(TextDecoration.BOLD, true))
                        .append(Component.text("!")
                                .color(nextSurvival.primary)));

                FileManager.saveServerConfig(serverConfig);
                return true;
            }
        }
        return false;
    }
}
