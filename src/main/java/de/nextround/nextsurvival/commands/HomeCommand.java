package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.PlayerLocation;
import de.nextround.nextsurvival.utilities.ServerConfig;
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
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /home <set>");
                return false;
            }

            if(args.length==0) {
                if(!serverConfig.getHomeLocations().containsKey(player.getUniqueId())) {
                    player.sendMessage(nextSurvival.PREFIX + " §cAt first you have to set a home --> /home set");
                    return false;
                }
                serverConfig.getHomeLocations().get(player.getUniqueId()).teleportPlayer(player);

                player.sendMessage(nextSurvival.PREFIX + " §3You are now at your home!");

                return true;
            } else {
                if(args[0].equalsIgnoreCase("set")) {
                    serverConfig.addHomeLocation(player, new PlayerLocation(player));

                    FileManager.saveServerConfigFile(serverConfig);
                    player.sendMessage(nextSurvival.PREFIX + " §3Your home is now set to this location!");
                    return true;
                }else{
                    player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /home <set>");
                    return false;
                }
            }
        } else if (command.getName().equalsIgnoreCase("back")) {
            if (args.length == 0) {
                if (serverConfig.getDeathLocations().containsKey(player.getUniqueId())) {
                    if(nextSurvival.instance.recentlyDied.contains(player)) {
                        serverConfig.getDeathLocations().get(player.getUniqueId()).teleportPlayer(player);

                        player.sendMessage(nextSurvival.PREFIX + " §3You are now at your death location!");
                    }else{
                        player.sendMessage(nextSurvival.PREFIX + " §3You ran out of time §f§l:/ §k§3There is no going back now. Sorry §f§l,_,");
                    }
                } else {
                    player.sendMessage(nextSurvival.PREFIX + " §cYou have no death location saved by the server!");
                }
                return true;
            }
        }else if (command.getName().equalsIgnoreCase("sethome")) {
            serverConfig.addHomeLocation(player, new PlayerLocation(player));

            FileManager.saveServerConfigFile(serverConfig);

            player.sendMessage(nextSurvival.PREFIX + " §3Your home is now set to this location!");
            return true;
        }
        return false;
    }
}
