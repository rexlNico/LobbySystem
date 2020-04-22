package de.rexlnico.lobbysystem.lobbywechsler;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.dytanic.cloudnet.common.document.gson.JsonDocument;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.provider.service.SpecificCloudServiceProvider;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import de.dytanic.cloudnet.ext.bridge.player.NetworkServiceInfo;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class LobbyWechsler implements Listener {

    public static final String GUI_NAME = "§8➥ §6Lobbywechsler";

    public Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, GUI_NAME);
        for (int i = 1; i < 6; i++) {
            SpecificCloudServiceProvider specificCloudServiceProvider = CloudNetDriver.getInstance().getCloudServiceProvider("Lobby-" + i);
            if (specificCloudServiceProvider == null || specificCloudServiceProvider.getServiceInfoSnapshot() == null) {
                inventory.setItem(i - 1, new ItemBuilder(Material.STAINED_CLAY, 1, 14).setName("§4Lobby " + i).build());
            } else {
                int online = ServiceInfoSnapshotUtil.getOnlineCount(specificCloudServiceProvider.getServiceInfoSnapshot());
                int max = ServiceInfoSnapshotUtil.getMaxPlayers(specificCloudServiceProvider.getServiceInfoSnapshot());
                boolean b2 = BridgePlayerManager.getInstance().getOnlinePlayer(player.getUniqueId()).getConnectedService().getServerName().equals("Lobby-" + i);
                if (online >= max) {
                    inventory.setItem(i - 1, new ItemBuilder(Material.STAINED_CLAY, 1, 4).enchantIftrue(b2).setLore("§8➥ §6" + online + "§8/§6" + max).setName("§6Lobby " + i).build());
                } else {
                    inventory.setItem(i - 1, new ItemBuilder(Material.STAINED_CLAY, 1, 5).enchantIftrue(b2).setLore("§8➥ §a" + online + "§8/§a" + max).setName("§aLobby " + i).build());
                }
            }
        }
        return inventory;
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = e.getPlayer();
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().getItemMeta() != null) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().equals("§6Lobbywechsler")) {
                        Inventory inventory = getInventory(player);
                        player.openInventory(inventory);
                    }
                }
            }
        }
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getInventory().getName().equals(GUI_NAME)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().getItemMeta() != null) {
                        e.setCancelled(true);
                        Player player = (Player) e.getWhoClicked();
                        if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§aLobby ")) {
                            if (!BridgePlayerManager.getInstance().getOnlinePlayer(player.getUniqueId()).getConnectedService().getServerName().replace("Lobby-", "").equals(e.getCurrentItem().getItemMeta().getDisplayName().replace("§aLobby ", ""))) {
                                player.playSound(player.getLocation(), Sound.DIG_WOOL, 1, 1);
                                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                                out.writeUTF("Connect");
                                out.writeUTF("Lobby-" + e.getCurrentItem().getItemMeta().getDisplayName().replace("§aLobby ", ""));
                                player.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
                            } else {
                                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                        }
                    }
                }
            }
        }
    }

}
