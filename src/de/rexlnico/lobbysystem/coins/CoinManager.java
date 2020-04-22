package de.rexlnico.lobbysystem.coins;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class CoinManager implements Listener {

    public static HashMap<Player, Integer> list;

    public CoinManager() {
        list = new HashMap<>();
        for (Player all : Bukkit.getOnlinePlayers()) {
            list.put(all, getMySQLCoins(all));
        }
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Main.getPlugin(), "Coins", (channel, player, bytes) -> {

            DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (channel.equals("Coins")) {
                try {
                    String message = in.readUTF();
                    if (message.equals("update")) {
                        update(player);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int getCoins(Player player) {
        return list.containsKey(player) ? list.get(player) : 0;
    }

    public void setCoins(Player player, int coins) {
        list.remove(player);
        list.put(player, coins);
        Main.getPlugin().getScoreboardManager().setSideScoreboard(player);
    }

    public boolean hasCoins(Player player, int coins) {
        return list.get(player) >= coins;
    }

    public void removeCoins(Player player, int cois) {
        int after = list.get(player) - cois;
        list.remove(player);
        list.put(player, (after < 0 ? 0 : after));
        Main.getPlugin().getScoreboardManager().setSideScoreboard(player);
    }

    public void addCoins(Player player, int coins) {
        int after = list.get(player) + coins;
        list.remove(player);
        list.put(player, after);
        Main.getPlugin().getScoreboardManager().setSideScoreboard(player);
    }

    public void update(Player player) {
        list.remove(player);
        list.put(player, getMySQLCoins(player));
        Main.getPlugin().getScoreboardManager().setSideScoreboard(player);
    }

    private int getMySQLCoins(Player player) {
        if (existUser(player)) {
            ResultSet res = Main.getPlugin().getMySQL().query("SELECT Coins FROM Coinmanager WHERE UUID = '" + UUIDFetcher.getUUID(player) + "'");
            try {
                if (res.next()) {
                    return res.getInt("Coins");
                }
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    public void save() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            save(a);
        }
    }

    private void save(Player player) {
        try {
            if (existUser(player)) {
                PreparedStatement ps = (PreparedStatement) Main.getPlugin().getMySQL().getConnection().prepareStatement("UPDATE Coinmanager SET Coins = ? WHERE UUID = ?");
                ps.setInt(1, getCoins(player));
                ps.setString(2, UUIDFetcher.getUUID(player).toString());
                ps.execute();
                ps.close();
            } else {
                PreparedStatement ps = Main.getPlugin().getMySQL().getConnection().prepareStatement("INSERT INTO Coinmanager VALUES (?, ?)");
                ps.setString(1, UUIDFetcher.getUUID(player).toString());
                ps.setInt(2, getCoins(player));
                ps.executeUpdate();
                ps.close();
            }
        } catch (Exception e) {
        }
    }

    private boolean existUser(Player player) {
        ResultSet res = Main.getPlugin().getMySQL().query("SELECT * FROM Coinmanager WHERE UUID = '" + UUIDFetcher.getUUID(player) + "'");
        try {
            if (res.next()) {
                return true;
            }
        } catch (Exception e2) {
        }
        return false;
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        list.put(player, getMySQLCoins(player));
        Main.getPlugin().getScoreboardManager().setSideScoreboard(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        save(player);
        list.remove(player);
    }

}
