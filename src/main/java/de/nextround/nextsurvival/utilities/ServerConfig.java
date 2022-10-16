package de.nextround.nextsurvival.utilities;

import com.google.gson.Gson;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

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

public class ServerConfig {

    private final HashMap<UUID, Boolean> passwordChecker;
    private final HashMap<UUID, PlayerLocation> homeLocations;
    private final HashMap<UUID, PlayerLocation> deathLocations;
    private final HashMap<UUID, String> prefixes;
    private final String password;

    /*
     * A class that is structured to easily save, create and update the config.json
     */
    public ServerConfig(String password) {
        this.passwordChecker = new HashMap<>();
        this.homeLocations = new HashMap<>();
        this.deathLocations = new HashMap<>();
        this.prefixes = new HashMap<>();
        this.password = password;
    }

    public HashMap<UUID, Boolean> getPasswordChecker() {
        return passwordChecker;
    }

    public HashMap<UUID, PlayerLocation> getHomeLocations() {
        return homeLocations;
    }

    public HashMap<UUID, PlayerLocation> getDeathLocations() {
        return deathLocations;
    }

    public HashMap<UUID, String> getPrefixes() {
        return prefixes;
    }

    public String getPassword() {
        return password;
    }

    public void addHomeLocation(Player player, PlayerLocation playerLocation) {
        if(homeLocations.containsKey(player.getUniqueId())) {
            homeLocations.replace(player.getUniqueId(), playerLocation);
        }else{
            homeLocations.put(player.getUniqueId(), playerLocation);
        }
    }

    public void addDeathLocation(Player player, PlayerLocation playerLocation) {
        if(deathLocations.containsKey(player.getUniqueId())) {
            deathLocations.replace(player.getUniqueId(), playerLocation);
        }else{
            deathLocations.put(player.getUniqueId(), playerLocation);
        }
    }

    public void addPasswordChecker(UUID uuid, boolean bool) {
        if(passwordChecker.containsKey(uuid)) {
            passwordChecker.replace(uuid, bool);
        } else {
            passwordChecker.put(uuid, bool);
        }
    }

    public void addPrefixes(UUID uuid, String string) {
        if(prefixes.containsKey(uuid)) {
            prefixes.replace(uuid, string);
        }else{
            prefixes.put(uuid, string);
        }
    }

    /*
     * Reads the config.json and returns a ServerConfig object with the content of the config.json
     */
    public static ServerConfig getServerConfig() {
        File file = new File("plugins/nextSurvival", "config.json");

        try {
            String userConfig = new String(Files.readAllBytes(Paths.get(file.getPath())));

            Gson gson = new Gson();

            return gson.fromJson(userConfig, ServerConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FileManager.createDefaultServerConfigFile(new ServerConfig("bamboowagon"));
    }

}
