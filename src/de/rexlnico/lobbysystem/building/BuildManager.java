package de.rexlnico.lobbysystem.building;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;

public class BuildManager implements Listener {

    private ArrayList<Player> canBuild;

    public BuildManager() {
        canBuild = new ArrayList<>();
    }

    public void setState(Player player, boolean state) {
        if (state && !canBuild.contains(player)) {
            canBuild.add(player);
        } else if (!state && canBuild.contains(player)) {
            canBuild.remove(player);
        }
    }

    public boolean canBuild(Player player) {
        return canBuild.contains(player);
    }


    @EventHandler
    public void on(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!canBuild(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (!canBuild(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!canBuild(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerDropItemEvent e) {
        Player player = (Player) e.getPlayer();
        if (!canBuild(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerPickupItemEvent e) {
        Player player = (Player) e.getPlayer();
        if (!canBuild(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!e.getClickedBlock().getState().getType().equals(Material.WOOD_BUTTON) && !e.getClickedBlock().getState().getType().equals(Material.STONE_BUTTON)) {
                Player player = e.getPlayer();
                if (!canBuild(player)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
