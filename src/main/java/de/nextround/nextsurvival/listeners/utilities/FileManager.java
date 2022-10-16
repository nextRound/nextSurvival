package de.nextround.nextsurvival.listeners.utilities;

import com.google.gson.Gson;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

public class FileManager {

    /**
     * Generates a new plugins/nextSurvival/config.json if it doesn't exist.
     * Writes the file with the provided ServerConfig
     * @param serverConfig The config you want to save
     * @return passed server configuration file
     */
    public static ServerConfig createDefaultServerConfigFile(ServerConfig serverConfig) {
        Gson gson = new Gson();
        String server = gson.toJson(serverConfig);

        File file = new File("plugins/nextSurvival/", "config.json");

        try {
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

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return serverConfig;
    }

    /*
     * Updates the plugins/nextSurvival/config.json with the new provided ServerConfig
     */
    public static void updateDefaultServerConfigFile(ServerConfig serverConfig) {
        Gson gson = new Gson();
        String server = gson.toJson(serverConfig);

        File file = new File("plugins/nextSurvival", "config.json");

        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(server);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
