package dev.mpab.survivingpays.commands.subcommands;

import dev.mpab.survivingpays.commands.SubCommand;
import dev.mpab.survivingpays.libraries.LuckPermsDataAccess;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class StatusSubCommand implements SubCommand {
    private static final Logger LOGGER = Logger.getLogger("SurvivingPays");
    private LuckPermsDataAccess luckPermsDA;

    public StatusSubCommand(LuckPermsDataAccess luckPermsDA) {
        this.luckPermsDA = luckPermsDA;
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getDescription() {
        return "Shows the current state of your personal keepInventory rule";
    }

    @Override
    public String getUsage() {
        return "/keepinv status";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 2) {
                if (player.hasPermission("keepinv.status.others")) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        if (luckPermsDA.isKeepInv(target)) {
                            player.sendMessage(ChatColor.AQUA + target.getName() + " currently has keepInventory " + ChatColor.GREEN + "on!");
                        } else {
                            player.sendMessage(ChatColor.AQUA + target.getName() + " currently has keepInventory " + ChatColor.RED + "off!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Player not found: " + args[1]);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not allowed to do this.");
                }
            } else {
                if (luckPermsDA.isKeepInv(player)) {
                    player.sendMessage(ChatColor.AQUA + "You currently have keepInventory " + ChatColor.GREEN + "on!");
                } else {
                    player.sendMessage(ChatColor.AQUA + "You currently have keepInventory " + ChatColor.RED + "off!");
                }
            }
        } else {
            if (args.length != 2) {
                LOGGER.warning("Running this command from this source requires the target name");
            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null) {
                    if (luckPermsDA.isKeepInv(target)) {
                        LOGGER.info(target.getName() + " currently has keepInventory " + "on!");
                    } else {
                        LOGGER.info(target.getName() + " currently has keepInventory " + "off!");
                    }
                } else {
                    LOGGER.warning("Player not found: " + args[1]);
                }
            }
        }
    }
}
