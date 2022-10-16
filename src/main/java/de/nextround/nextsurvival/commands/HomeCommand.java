package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.listeners.utilities.FileManager;
import de.nextround.nextsurvival.listeners.utilities.PlayerLocation;
import de.nextround.nextsurvival.listeners.utilities.ServerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

public class HomeCommand implements CommandExecutor {

    private final nextSurvival instance;

    public HomeCommand(nextSurvival instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        Player player = (Player) sender;
        ServerConfig serverConfig = ServerConfig.getServerConfig();

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

                player.sendMessage(nextSurvival.PREFIX + " §9You are now at your home!");

                return true;
            } else if(args.length==1) {
                if(args[0].equalsIgnoreCase("set")) {
                    serverConfig.addHomeLocation(player, new PlayerLocation(player));

                    FileManager.updateDefaultServerConfigFile(serverConfig);
                    player.sendMessage(nextSurvival.PREFIX + " §9Your home is now set to this location!");
                    return true;
                }else{
                    player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /home <set>");
                    return false;
                }
            }
        } else if (command.getName().equalsIgnoreCase("back")) {
            if (args.length == 0) {
                if (serverConfig.getDeathLocations().containsKey(player.getUniqueId())) {
                    if(instance.recentlyDied.contains(player)) {
                        serverConfig.getDeathLocations().get(player.getUniqueId()).teleportPlayer(player);

                        player.sendMessage(nextSurvival.PREFIX + " §9You are now at your death location!");
                    }else{
                        player.sendMessage(nextSurvival.PREFIX + " §9You ran out of time §d§l:/ §k§9There is no going back now. Sorry §d§l; -;");
                    }
                } else {
                    player.sendMessage(nextSurvival.PREFIX + " §cYou have no death location saved by the server!");
                }
            }
        }else if (command.getName().equalsIgnoreCase("sethome")) {
            serverConfig.addHomeLocation(player, new PlayerLocation(player));

            FileManager.updateDefaultServerConfigFile(serverConfig);

            player.sendMessage(nextSurvival.PREFIX + " §9Your home is now set to this location!");
            return true;
        }
        return false;
    }
}
