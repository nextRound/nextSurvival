package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
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

public class HorseSpeedCommand implements CommandExecutor {

    /*
     * Creates a /getHorseSpeed command
     */
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String arg, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("getHorseSpeed")) {
            try {
                Entity entity = player.rayTraceEntities(10).getHitEntity();

                if (entity != null && entity.getType() == EntityType.HORSE) {
                    Horse horse = (Horse) entity;
                    AttributeInstance instance = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" Speed of ")
                                    .color(nextSurvival.primary))
                            .append(Component.text(horse.getName())
                                    .color(nextSurvival.highlight_yellow))
                            .append(Component.text(" is: ")
                                    .color(nextSurvival.primary))
                            .append(Component.text(Math.ceil(instance.getValue() * 43.17d * 100d)/100d)
                                    .color(nextSurvival.highlight_blue)
                                    .decoration(TextDecoration.BOLD, true))
                            );

                } else {
                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" No horse found!")
                                    .color(nextSurvival.error)));

                }
                return true;
            }catch (NullPointerException e){
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" No horse/entity in range!")
                                .color(nextSurvival.error)));
                return true;
            }
        }
        return false;
    }
}
