package dev.mpab.survivingpays.commands.subcommands;

import dev.mpab.survivingpays.commands.SubCommand;
import dev.mpab.survivingpays.libraries.LuckPermsDataAccess;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class ToggleSubCommand implements SubCommand {
    private static final Logger LOGGER = Logger.getLogger("SurvivingPays");
    private LuckPermsDataAccess luckPermsDA;

    public ToggleSubCommand(LuckPermsDataAccess luckPermsDA) {
        this.luckPermsDA = luckPermsDA;
    }

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getDescription() {
        return "Toggles your current state of the keepInventory gamerule";
    }

    @Override
    public String getUsage() {
        return "/keepinv toggle";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 2) {
                if (player.hasPermission("keepinv.toggle.others")) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        luckPermsDA.toggleKeepInv(target);
                        player.sendMessage(ChatColor.AQUA + "You have just turned keepInventory for " + target.getName() + " " + (!luckPermsDA.isKeepInv(target) ? ChatColor.GREEN + "on!" : ChatColor.RED + "off!"));
                    } else {
                        player.sendMessage(ChatColor.RED + "Cannot find player: " + args[1]);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not allowed to do this.");
                }
            } else {
                luckPermsDA.toggleKeepInv(player);
                player.sendMessage(ChatColor.AQUA + "You have just turned keepInventory " + (!luckPermsDA.isKeepInv(player) ? ChatColor.GREEN + "on!" : ChatColor.RED + "off!"));
            }
        } else {
            if (args.length != 2) {
                LOGGER.warning("Running this command from this source requires the target name");
            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null) {
                    luckPermsDA.toggleKeepInv(target);
                    LOGGER.info("You have just turned keepInventory for " + target.getName() + " " + (!luckPermsDA.isKeepInv(target) ?  "on!" : "off!"));
                } else {
                    LOGGER.warning("Cannot find player: " + args[1]);
                }
            }
        }
    }
}
