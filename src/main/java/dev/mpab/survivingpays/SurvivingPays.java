package dev.mpab.survivingpays;

import dev.mpab.survivingpays.commands.KeepInvCommandManager;
import dev.mpab.survivingpays.libraries.LuckPermsDataAccess;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SurvivingPays extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger("SurvivingPays");

    @Override
    public void onEnable() {
        // get luckperms instance and instantiate DAL
        LuckPerms luckPerms = getServer().getServicesManager().getRegistration(LuckPerms.class).getProvider();
        LuckPermsDataAccess luckPermsDA = new LuckPermsDataAccess(luckPerms);

        // register command
        getCommand("keepinv").setExecutor(new KeepInvCommandManager(luckPermsDA));

        LOGGER.info("Started SurvivingPays plugin...");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOGGER.info("Shutting down SurvivingPays plugin...");
    }
}
