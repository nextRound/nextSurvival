package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.listeners.utilities.FileManager;
import de.nextround.nextsurvival.listeners.utilities.ServerConfig;
import org.bukkit.Bukkit;
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

public class PrefixCommand implements CommandExecutor {

    /*
     * Creates a /prefix [name] [prefix] command
     */
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("prefix")) {
            if(args.length>=3) {
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /prefix [name] [prefix]");
                return false;
            }
            if(args.length==0) {
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /prefix [name] [prefix]");
                return false;
            }else if(args.length==1) {
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /prefix [name] [prefix]");
                return false;
            }else if(args.length==2) {
                ServerConfig serverConfig = ServerConfig.getServerConfig();

                if(Bukkit.getServer().getPlayer(args[0]) != null) {
                    serverConfig.addPrefixes(Bukkit.getServer().getPlayer(args[0]).getUniqueId(), args[1]);
                }else{
                    Bukkit.getServer().broadcastMessage(nextSurvival.PREFIX + " &cPlayer is not on the server!");
                }

                player.sendMessage(nextSurvival.PREFIX + " §9You have set the prefix '"
                        +args[1].replace("&", "§")+"§9' for §r"+args[0]+"§9!");

                FileManager.updateDefaultServerConfigFile(serverConfig);
            }
        }
        return false;
    }
}
