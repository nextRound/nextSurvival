package de.nextround.nextsurvival.listeners;

import de.nextround.nextsurvival.nextSurvival;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

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

public class GriefListener implements Listener {

    public GriefListener(nextSurvival instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    /*
     * Blocks every block change for every entity that is not PRIMED_TNT
     */
    @EventHandler
    public void onCreeper(EntityExplodeEvent e) {
        if (e.getEntityType() != EntityType.PRIMED_TNT) {
            e.blockList().clear();
        }
    }

    /*
     * Cancels the EntityChangeBlockEvent for every entity that is an ENDERMAN
     */
    @EventHandler
    public void onEndermanPickup(EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}