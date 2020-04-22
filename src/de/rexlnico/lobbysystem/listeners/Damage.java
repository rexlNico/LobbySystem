package de.rexlnico.lobbysystem.listeners;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.teleporter.TeleportManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener {

    @EventHandler
    public void on(EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                Main.getPlugin().getTeleportManager().teleportPlayer((Player) e.getEntity(), TeleportManager.Teleports.SPAWN);
            }
        }
    }

}
