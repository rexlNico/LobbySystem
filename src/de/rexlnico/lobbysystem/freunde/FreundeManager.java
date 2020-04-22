package de.rexlnico.lobbysystem.freunde;

import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class FreundeManager implements Listener {

    private HashMap<Player, Freunde> list;

    public FreundeManager() {
        list = new HashMap<>();
        for (Player all : Bukkit.getOnlinePlayers()) {
            Freunde f = new Freunde(all);
            list.put(all, f);
            for (UUID uuid : f.getFriends()) {
                if (!FreundeGui.skulls.containsKey(uuid))
                    FreundeGui.skulls.put(uuid, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(UUIDFetcher.getName(uuid)).build());
            }
            for (UUID uuid : f.getRequests()) {
                if (!FreundeGui.skulls.containsKey(uuid))
                    FreundeGui.skulls.put(uuid, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(UUIDFetcher.getName(uuid)).build());
            }
        }

        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Main.getPlugin(), "Freunde", (channel, player, bytes) -> {

            DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (channel.equals("Freunde")) {
                try {
                    String message = in.readUTF();
                    if (message.startsWith("update")) {
                        list.get(player).update();
                        BridgePlayerManager.getInstance().getOnlinePlayers();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public HashMap<Player, Freunde> getList() {
        return list;
    }

    public Freunde getFreund(Player player){
        return list.get(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Freunde freunde = new Freunde(player);
        list.put(player, freunde);
        Main.getPlugin().getScoreboardManager().setSideScoreboard(player);
        for (UUID uuid : freunde.getFriends()) {
            if (!FreundeGui.skulls.containsKey(uuid))
                FreundeGui.skulls.put(uuid, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(UUIDFetcher.getName(uuid)).build());
        }
        for (UUID uuid : freunde.getRequests()) {
            if (!FreundeGui.skulls.containsKey(uuid))
                FreundeGui.skulls.put(uuid, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(UUIDFetcher.getName(uuid)).build());
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        list.remove(player);
    }

}
