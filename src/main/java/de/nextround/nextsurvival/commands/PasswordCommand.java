package de.nextround.nextsurvival.commands;

import de.nextround.nextsurvival.nextSurvival;
import de.nextround.nextsurvival.utilities.FileManager;
import de.nextround.nextsurvival.utilities.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PasswordCommand implements CommandExecutor  {

    /*
     * Creates a /password <password-string>
     */
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String arg, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("password")) {
            if(args.length==0) {
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" No password provided. Syntax: /password [password-string]")
                                .color(nextSurvival.error)));
                return false;
            } else if(args.length==1) {
                String password = args[0];
                ServerConfig serverConfig = nextSurvival.serverConfig;

                if((password.equals(serverConfig.getPassword()))) {

                    nextSurvival.instance.password.remove(player);
                    serverConfig.addPasswordChecker(player.getUniqueId(), true);

                    player.setGameMode(GameMode.SURVIVAL);

                    player.sendMessage(nextSurvival.PREFIX
                            .append(Component.text(" You got it right! ")
                                    .color(nextSurvival.primary))
                            .append(Component.text(":::")
                                    .color(nextSurvival.highlight_yellow)
                                    .decoration(TextDecoration.OBFUSCATED, true)
                                    .decoration(TextDecoration.BOLD, true))
                            .append(Component.text(" Yaaay Wooop Wooop ")
                                    .color(nextSurvival.highlight_blue)
                                    .decoration(TextDecoration.BOLD, true))
                            .append(Component.text(":::")
                                    .color(nextSurvival.highlight_yellow)
                                    .decoration(TextDecoration.OBFUSCATED, true)
                                    .decoration(TextDecoration.BOLD, true)));

                    System.out.println(nextSurvival.PREFIX + " " + player.getName() + " is now a member of the server!");



                    FileManager.saveServerConfig(serverConfig);
                }
            } else {
                player.sendMessage(nextSurvival.PREFIX
                        .append(Component.text(" Syntax: /password [password-string]")
                                .color(nextSurvival.error)));
                return false;
            }
        }

        return false;
    }
}
