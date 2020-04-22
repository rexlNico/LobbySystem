package de.rexlnico.lobbysystem.teleporter;

import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class TeleportManager {

    public enum Teleports {

        SPAWN("§bSpawn", Material.MAGMA_CREAM, "Spawn.cfg", 31, true),
        TTT("§4TTT", Material.IRON_SWORD, "TTT.cfg", 19, true),
        BW("§aBlockWars", Material.EMERALD_BLOCK, "Blockwars.cfg", 25, true),
        SW("§eSkyWars", Material.GRASS, "Skywars.cfg", 38, true),
        CHALANGES("§5Chalanges", Material.SIGN, "Chalanges.cfg", 14, true),
        KNOCKY("§3Knocky", Material.STICK, "Knocky.cfg", 42, true),
        ALLVSALL("§cAll VS All", Material.FIREWORK_CHARGE, "Allvsall.cfg", 12, true);

        private String name;
        private Material item;
        private String configPath;
        private int slot;
        private boolean enabled;

        Teleports(String name, Material item, String configPath, int slot, boolean enabled) {
            this.name = name;
            this.item = item;
            this.configPath = configPath;
            this.slot = slot;
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public Material getItem() {
            return item;
        }

        public ItemStack getFullItem() {
            return new ItemBuilder(getItem(), 1).setName(getName()).build();
        }

        public int getSlot() {
            return slot;
        }

        public YamlConfiguration getConfig() {
            return YamlConfiguration.loadConfiguration(getFile());
        }

        public File getFile() {
            return new File("plugins/LobbySystem/Teleporter/" + getConfigPath());
        }

        public String getConfigPath() {
            return configPath;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    public static final String TELEPORTER_NAME = "§8➥ §bTeleporter";

    private Inventory teleporter;
    private HashMap<Teleports, Location> locations;

    public TeleportManager() {
        teleporter = Bukkit.createInventory(null, 9 * 6, TELEPORTER_NAME);
        locations = new HashMap<>();
        ItemBuilder.fillInv(teleporter);
        for (Teleports teleport : Teleports.values()) {
            teleporter.setItem(teleport.getSlot(), teleport.getFullItem());
        }
        updateAll();
    }

    public Inventory getTeleporter() {
        return teleporter;
    }

    public void updateAll() {
        for (Teleports teleport : Teleports.values()) {
            update(teleport);
        }
    }

    public void update(Teleports teleport) {
        if (locations.containsKey(teleport)) {
            locations.remove(teleport);
        }
        if (teleport.getFile().exists()) {
            if (teleport.getConfig().get("Location") != null) {
                locations.put(teleport, (Location) teleport.getConfig().get("Location"));
            }
        }
    }

    public void openTeleporter(Player player) {
        player.openInventory(teleporter);
    }

    public Teleports getTeleport(ItemStack item) {
        if (item == null) return null;
        if (item.getItemMeta() == null) return null;
        for (Teleports teleport : Teleports.values()) {
            if (teleport.getItem().equals(item.getType())) {
                if (teleport.getName().equals(item.getItemMeta().getDisplayName())) {
                    return teleport;
                }
            }
        }
        return null;
    }

    public void teleportPlayer(Player player, Teleports teleport) {
        if (teleport != null) {
            if (locations.containsKey(teleport)) {
                if (teleport.isEnabled() || player.hasPermission(Permissions.TELEPORTER_USE.getPermission() + ".*") || player.hasPermission(Permissions.TELEPORTER_USE.getPermission() + "." + teleport.getConfigPath().replace(".yml", ""))) {
                    Location loc = locations.get(teleport);
                    player.teleport(loc);
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                }
                return;
            }
            if (Permissions.hasPermissions(player, Permissions.RANG_ADMIN, Permissions.RANG_DEVELOPER)) {
                player.sendMessage("§4Die Location für " + teleport.getName() + " §4ist nicht gesetzt!");
            }
        }
        player.closeInventory();
    }

}
