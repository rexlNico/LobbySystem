package de.rexlnico.lobbysystem.teleporter;

import de.rexlnico.lobbysystem.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class UseTeleporter implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = e.getPlayer();
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().getItemMeta() != null) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§bTeleporter")) {
                        player.openInventory(Main.getPlugin().getTeleportManager().getTeleporter());
                    }
                }
            }
        }
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getName().equals(TeleportManager.TELEPORTER_NAME)) {
                e.setCancelled(true);
                TeleportManager.Teleports teleport = Main.getPlugin().getTeleportManager().getTeleport(e.getCurrentItem());
                if (teleport != null) {
                    Main.getPlugin().getTeleportManager().teleportPlayer(player, teleport);
                }
            }
        }
    }

}
