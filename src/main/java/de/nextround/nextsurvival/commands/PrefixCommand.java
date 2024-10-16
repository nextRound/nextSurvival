package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
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
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /prefix [name] [prefix]");
                return false;
            }
            if(args.length==0) {
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /prefix [name] [prefix]");
                return false;
            }else if(args.length==1) {
                player.sendMessage(nextSurvival.PREFIX + " §cSyntax --> /prefix [name] [prefix]");
                return false;
            }else {
                ServerConfig serverConfig = nextSurvival.serverConfig;

                if(Bukkit.getServer().getPlayer(args[0]) != null) {
                    serverConfig.addPrefixes(Bukkit.getServer().getPlayer(args[0]).getUniqueId(), args[1]);
                }else{
                    Bukkit.getServer().broadcastMessage(nextSurvival.PREFIX + " §cPlayer is not on the server!");
                    return true;
                }

                player.sendMessage(nextSurvival.PREFIX + " §3You have set the prefix '§r"
                        +args[1].replace("&", "§")+"§3' for §r"+args[0]+"§3!");

                FileManager.saveServerConfigFile(serverConfig);
                return true;
            }
        }
        return false;
    }
}
