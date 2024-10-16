package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

public class ConnectionListeners implements Listener {

    public ConnectionListeners() {
        Bukkit.getPluginManager().registerEvents(this, nextSurvival.instance);
    }

    /*
     * Displays a beautiful connect message depending on the prefix (if one exists)
     * Checks if the password entry of the player if true or false
     * if the player UUID doesn't exist it creates an entry with the value false in the config.json
     */
    @EventHandler
    public void onJoinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ServerConfig serverConfig = nextSurvival.serverConfig;

        if(serverConfig.getPrefixes().containsKey(player.getUniqueId())) {
            event.setJoinMessage("§7[§a+§7] §r"+ serverConfig.getPrefixes().get(player.getUniqueId()).replace("&","§") +" §5"+player.getName());
        }else{
            event.setJoinMessage("§7[§a+§7]§5 "+player.getName());
        }

        if(!serverConfig.getPasswordChecker().containsKey(player.getUniqueId())) {
            serverConfig.addPasswordChecker(player.getUniqueId(), false);

            nextSurvival.instance.password.add(player);
            player.setGameMode(GameMode.ADVENTURE);

            player.sendMessage(nextSurvival.PREFIX + " §4Please type the server §aPASSWORD §4in the chat!");

            FileManager.saveServerConfigFile(serverConfig);
        }else if(!serverConfig.getPasswordChecker().get(player.getUniqueId())) {
            nextSurvival.instance.password.add(player);
            player.setGameMode(GameMode.ADVENTURE);

            player.sendMessage(nextSurvival.PREFIX + " §4Please type the server §a§lPASSWORD §4in the chat!");
            player.sendMessage(nextSurvival.PREFIX + " §3While doing that you §a§lACCEPT §3tall of our server rules!");
        }
    }

    /*
     * Displays a beautiful disconnect message depending on the prefix (if one exists)
     */
    @EventHandler
    public void onDisconnectListener(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ServerConfig serverConfig = nextSurvival.serverConfig;

        if(serverConfig.getPrefixes().containsKey(player.getUniqueId())) {
            event.setQuitMessage("§7[§c-§7] §r"+ serverConfig.getPrefixes().get(player.getUniqueId()).replace("&","§") +" §5"+player.getName());
        }else {
            event.setQuitMessage("§7[§c-§7]§5 " + player.getName());
        }
    }
}
