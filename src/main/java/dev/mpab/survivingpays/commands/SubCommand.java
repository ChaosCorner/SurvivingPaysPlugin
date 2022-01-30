package dev.mpab.survivingpays.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface SubCommand {
    String getName();
    String getDescription();
    String getUsage();
    void perform(CommandSender sender, String[] args);
}
