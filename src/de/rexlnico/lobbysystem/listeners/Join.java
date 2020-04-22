package de.rexlnico.lobbysystem.listeners;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.Permissions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Join implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e){
        e.setQuitMessage(null);
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        Main.getPlugin().getScoreboardManager().setScoreboard(player);
        giveJoinItems(player);
    }

    public static void giveJoinItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS, 1).setName("§bTeleporter").build());
        player.getInventory().setItem(7, new ItemBuilder(Material.NETHER_STAR, 1).setName("§6Lobbywechsler").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("§5Freundegui").build());
        if (Permissions.hasPermissions(player, Permissions.RANG_ADMIN, Permissions.RANG_DEVELOPER, Permissions.RANG_BUILDER, Permissions.RANG_MODERATOR, Permissions.RANG_SUPPORTER, Permissions.RANG_YOUTUBER)) {
            player.getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG, 1).setName("§7Autonick "+(Main.getPlugin().getNickManager().getState(player) ? "§aActive" : "§4Inactive")).build());
        }

    }

}
