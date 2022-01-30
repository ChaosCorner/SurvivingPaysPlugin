package dev.mpab.survivingpays.libraries;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class LuckPermsDataAccess {
    private static final Logger LOGGER = Logger.getLogger("SurvivingPays");
    private static final String GROUP_KEEPINV_OFF = "meta_survival18_nokeepinv";

    private LuckPerms api;

    public LuckPermsDataAccess(LuckPerms api) {
        this.api = api;
    }

    public LuckPerms getLuckPermsApi() {
        return api;
    }

    public boolean isKeepInv(Player player) {
        return !player.hasPermission("group." + GROUP_KEEPINV_OFF);
    }

    public void toggleKeepInv(Player player) {
        boolean isKeepInv = isKeepInv(player);

        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            LOGGER.info(String.format("%s is toggling keepInventory from %s to %s", player.getName(), isKeepInv, !isKeepInv));
            if (isKeepInv) {
                user.data().add(InheritanceNode.builder(GROUP_KEEPINV_OFF).build());
            } else {
                user.data().remove(InheritanceNode.builder(GROUP_KEEPINV_OFF).build());
            }
        });
    }
}
