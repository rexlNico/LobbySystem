package de.rexlnico.lobbysystem.dailyrewards;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DailyRewardListeners implements Listener {

    public static final String GUI_NAME = "§8➥ §bTägliche Belohnungen";

    @EventHandler
    public void on(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Villager) {
            if (e.getRightClicked().getCustomName().equals(DailyRewardCommand.NAME)) {
                Player player = e.getPlayer();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, GUI_NAME);
                        ItemBuilder.fillInv(inventory);
                        inventory.setItem(0, new ItemBuilder(Material.PAPER).setName("§7Standard Belohnungen").enchantIftrue(true).build());
                        if (Permissions.hasPermissions(player, Permissions.RANG_ADMIN, Permissions.RANG_YOUTUBER, Permissions.RANG_SUPPORTER, Permissions.RANG_DEVELOPER, Permissions.RANG_BUILDER, Permissions.RANG_MODERATOR, Permissions.RANG_PREMIUM)) {
                            inventory.setItem(4, new ItemBuilder(Material.PAPER).setName("§6Premium Belohnungen").enchantIftrue(true).build());
                        } else {
                            inventory.setItem(4, new ItemBuilder(Material.PAPER).setName("§6Premium Belohnungen").enchantIftrue(false).build());
                        }
                        player.openInventory(inventory);
                    }
                }.runTaskLater(Main.getPlugin(), 1);
            }
        }
    }

}
