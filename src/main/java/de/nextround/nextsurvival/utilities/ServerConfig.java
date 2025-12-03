package de.nextround.nextsurvival.utilities;

import org.bukkit.entity.Player;

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
 *    Author: Nicole Scheitler (nextRound)
 *    Copyright - GNU GPLv3 (C) Nicole Scheitler
 *
 *
 */

public class ServerConfig {

    private String configVersion;
    private HashMap<UUID, Boolean> passwordChecker;
    private HashMap<UUID, PlayerLocation> homeLocations;
    private HashMap<UUID, PlayerLocation> deathLocations;
    private HashMap<UUID, String> prefixes;
    private String password;

    /*
     * A class that is structured to easily save, create and update the config.json
     */
    public ServerConfig(String password, String configVersion) {
        this.configVersion = configVersion;
        this.passwordChecker = new HashMap<>();
        this.homeLocations = new HashMap<>();
        this.deathLocations = new HashMap<>();
        this.prefixes = new HashMap<>();
        this.password = password;
    }

    public String getConfigVersion() {
        return configVersion;
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

    public void fillNullValuesWithDefaultValues(String password, String configVersion) {
        if (this.configVersion == null || !this.configVersion.equals(configVersion)) {
            this.configVersion = configVersion;
        }
        if (passwordChecker == null) {
            this.passwordChecker = new HashMap<>();
        }
        if (homeLocations == null) {
            this.homeLocations = new HashMap<>();
        }
        if (deathLocations == null) {
            this.deathLocations = new HashMap<>();
        }
        if (prefixes == null) {
            this.prefixes = new HashMap<>();
        }
        if (this.password == null) {
            this.password = password;
        }
    }
}
