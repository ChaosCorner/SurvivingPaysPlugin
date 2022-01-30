package dev.mpab.survivingpays.commands;

import dev.mpab.survivingpays.commands.subcommands.StatusSubCommand;
import dev.mpab.survivingpays.commands.subcommands.ToggleSubCommand;
import dev.mpab.survivingpays.libraries.LuckPermsDataAccess;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class KeepInvCommandManager implements TabExecutor {
    private Map<String, SubCommand> subcommands = new HashMap<>();

    public KeepInvCommandManager(LuckPermsDataAccess luckPermsDA) {
        subcommands.put("status", new StatusSubCommand(luckPermsDA));
        subcommands.put("toggle", new ToggleSubCommand(luckPermsDA));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || args.length > 2) {
            return false;
        }

        if (subcommands.get(args[0]) == null) {
            return false;
        }

        subcommands.get(args[0]).perform(sender, args);

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("toggle", "status");
        } else if (args.length == 2) {
            return Bukkit.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).toList();
        }
        return null;
    }
}
