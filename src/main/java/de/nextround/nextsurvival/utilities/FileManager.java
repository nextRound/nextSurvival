package de.nextround.nextsurvival.utilities;

import com.google.gson.Gson;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

public class FileManager {

    public static final String configVersion = "1.0";

    /**
     * Generates a new plugins/nextSurvival/config.json if it doesn't exist.
     * Writes the file with the provided ServerConfig
     * @return passed server configuration file
     */
    public static ServerConfig createDefaultServerConfigFile() throws IOException {
        Gson gson = new Gson();
        ServerConfig serverConfig = new ServerConfig("bamboowagon", configVersion);
        String server = gson.toJson(serverConfig);

        File file = new File("plugins/nextSurvival/", "config.json");

        if(!file.getParentFile().exists()) {
            if(file.getParentFile().mkdirs()) {
                Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3Plugin directory has been created!");
            }
        }

        if (file.createNewFile()) {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(server);
            fileWriter.close();

            Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §3Config-File has been created!");
            return serverConfig;
        }

        return serverConfig;
    }

    /*
     * Reads the config.json and returns a ServerConfig object with the content of the config.json
     */
    public static ServerConfig getServerConfig() throws IOException {
        File file = new File("plugins/nextSurvival", "config.json");

        if (!file.exists()) {
            return FileManager.createDefaultServerConfigFile();
        } else {
            String userConfig = new String(Files.readAllBytes(Paths.get(file.getPath())));

            Gson gson = new Gson();

            return gson.fromJson(userConfig, ServerConfig.class);
        }
    }

    /*
     * Updates the plugins/nextSurvival/config.json with the new provided ServerConfig
     */
    public static void saveServerConfig(ServerConfig serverConfig) {
        if(!serverConfig.getConfigVersion().equals(configVersion)) {
            serverConfig.fillNullValuesWithDefaultValues("bamboowagon", configVersion);
        }

        Gson gson = new Gson();
        String server = gson.toJson(serverConfig);

        File file = new File("plugins/nextSurvival", "config.json");

        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(server);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§8[§2nextSurvival§8] §cConfig file could not be saved!");
        }
    }
}
