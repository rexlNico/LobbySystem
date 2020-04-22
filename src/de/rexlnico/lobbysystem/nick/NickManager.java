package de.rexlnico.lobbysystem.nick;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NickManager implements Listener {

    public static HashMap<Player, Boolean> nick;

    public NickManager() {
        nick = new HashMap<>();
        for (Player a : Bukkit.getOnlinePlayers()) {
            addUser(a);
        }
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        addUser(e.getPlayer());
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        save(e.getPlayer());
        nick.remove(e.getPlayer());
    }

    private void addUser(Player player) {
        if (!nick.containsKey(player)) {
            ResultSet res = Main.getPlugin().getMySQL().query("SELECT Aktive FROM Nickuser WHERE UUID = '" + UUIDFetcher.getUUID(player) + "'");
            try {
                if (res.next()) {
                    nick.put(player, Boolean.valueOf(res.getString("Aktive")));
                } else {
                    nick.put(player, false);
                }
            } catch (Exception e2) {
                nick.put(player, false);
            }
        }
    }

    public boolean getState(Player p) {
        if (nick.get(p)) {
            return true;
        }
        return false;
    }

    public void setState(Player player, boolean b) {
        if (nick.containsKey(player)) nick.remove(player);
        nick.put(player, b);
    }

    public void save() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            save(a);
        }
    }

    private void save(Player player) {
        try {
            if (existUser(player)) {
                PreparedStatement ps = (PreparedStatement) Main.getPlugin().getMySQL().getConnection().prepareStatement("UPDATE Nickuser SET Aktive = ? WHERE UUID = ?");
                ps.setString(1, String.valueOf(getState(player)));
                ps.setString(2, UUIDFetcher.getUUID(player).toString());
                ps.execute();
                ps.close();
            } else {
                PreparedStatement ps = Main.getPlugin().getMySQL().getConnection().prepareStatement("INSERT INTO Nickuser VALUES (?, ?)");
                ps.setString(1, UUIDFetcher.getUUID(player).toString());
                ps.setString(2, String.valueOf(getState(player)));
                ps.executeUpdate();
                ps.close();
            }
        } catch (Exception e) {
        }
    }

    private boolean existUser(Player player) {
        ResultSet res = Main.getPlugin().getMySQL().query("SELECT * FROM Nickuser WHERE UUID = '" + UUIDFetcher.getUUID(player) + "'");
        try {
            if (res.next()) {
                return true;
            }
        } catch (Exception e2) {
        }
        return false;
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Player player = e.getPlayer();
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().getItemMeta() != null) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().startsWith("§7Autonick ")) {
                        setState(player, !getState(player));
                        player.getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG, 1).setName("§7Autonick " + (Main.getPlugin().getNickManager().getState(player) ? "§aActive" : "§4Inactive")).build());
                        player.playSound(player.getLocation(), Sound.LAVA_POP, 1, 1);
                    }
                }
            }
        }
    }

}
