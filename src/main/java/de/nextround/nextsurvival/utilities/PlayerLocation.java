package de.nextround.nextsurvival.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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

public class PlayerLocation {

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    /*
     * A class that is structured to easily save everything important to a player location
     */
    public PlayerLocation(Player player) {
        this.world = player.getWorld().getName();
        this.x = player.getLocation().toVector().getX();
        this.y = player.getLocation().toVector().getY();
        this.z = player.getLocation().toVector().getZ();
        this.yaw = player.getLocation().getYaw();
        this.pitch = player.getLocation().getPitch();
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    /*
     * Teleports the provided Player to the location of the PlayerLocation object
     */
    public void teleportPlayer(Player player) {
        Location location = new Vector(x, y, z).toLocation(getWorld());
        location.setPitch(pitch);
        location.setYaw(yaw);
        if (!location.getChunk().isLoaded()) {
            location.getChunk().load();
        }
        player.teleport(location);
    }
}
