package de.rexlnico.lobbysystem.freunde;

import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.UUIDFetcher;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class FreundeGui implements Listener {

    public final String GUI_NAME = "§8➥ §5Freunde";
    public final String AGUI_NAME = "§8➥ §5Anfragen";
    private static HashMap<Player, Integer> page = new HashMap<>();
    public static HashMap<UUID, ItemStack> skulls = new HashMap<>();

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (e.getInventory() != null) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§5Freundegui")) {
                        Player player = (Player) e.getWhoClicked();
                        if (page.containsKey(player)) page.remove(player);
                        player.openInventory(getInventory(player, 1));
                        page.put(player, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void on2(InventoryClickEvent e) {
        if (e.getInventory() != null) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    if (e.getInventory().getName().equals(GUI_NAME)) {
                        Player player = (Player) e.getWhoClicked();
                        if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
                        if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                            String name = e.getCurrentItem().getItemMeta().getDisplayName();
                            if (name.equals("§7Zurück")) {
                                if (page.get(player) > 1) {
                                    int after = page.remove(player) - 1;
                                    page.put(player, after);
                                    player.openInventory(getInventory(player, page.get(player)));
                                }
                            } else if (name.equals("§7Vor")) {
                                if ((double) (page.get(player) + 1) <= (double) (Main.getPlugin().getFreundeManager().getFreund(player).getFriends().size() / 36)) {
                                    int after = page.remove(player) + 1;
                                    page.put(player, after);
                                    player.openInventory(getInventory(player, page.get(player)));
                                }
                            } else if (!name.startsWith("§7Seite ") && !name.startsWith("§5Profil")) {
                                String playerName = name.replace("§a", "").replace("§c", "");
                                UUID uuid = UUIDFetcher.getUUID(playerName);
                            }
                        } else if (e.getCurrentItem().getType().equals(Material.BOOK_AND_QUILL)) {
                            page.remove(player);
                            page.put(player, 1);
                            player.openInventory(getRequestInventory(player, 1));
                        }
                    } else if (e.getInventory().getName().equals(AGUI_NAME)) {
                        Player player = (Player) e.getWhoClicked();
                        if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
                        if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                            String name = e.getCurrentItem().getItemMeta().getDisplayName();
                            if (name.equals("§7Zurück")) {
                                if (page.get(player) > 1) {
                                    int after = page.remove(player) - 1;
                                    page.put(player, after);
                                    player.openInventory(getInventory(player, page.get(player)));
                                }
                            } else if (name.equals("§7Vor")) {
                                if ((double) (page.get(player) + 1) <= (double) (Main.getPlugin().getFreundeManager().getFreund(player).getFriends().size() / 36)) {
                                    int after = page.remove(player) + 1;
                                    page.put(player, after);
                                    player.openInventory(getInventory(player, page.get(player)));
                                }
                            } else if (!name.startsWith("§7Seite ")) {
                                UUID uuid = UUIDFetcher.getUUID(name.replace("§7", ""));

                            }
                        }
                    }
                }
            }
        }
    }

    private Inventory getInventory(Player player, int page) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, GUI_NAME);
        for (int i = 36; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15).setName("§6").build());
        }
        inv.setItem(45, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setTexture("http://textures.minecraft.net/texture/37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645").setName("§7Zurück").build());
        inv.setItem(48, new ItemBuilder(Material.BOOK_AND_QUILL, 1).setName("§5Anfragen").setAmount(Main.getPlugin().getFreundeManager().getFreund(player).getRequests().size()).build());
        inv.setItem(50, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("§7Seite " + page).setTexture(getTexture(page)).build());
        inv.setItem(53, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setTexture("http://textures.minecraft.net/texture/682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e").setName("§7Vor").build());
        int slot = 0;
        Freunde freunde = Main.getPlugin().getFreundeManager().getFreund(player);
        for (int i = (36 * (page - 1)); i < Math.min((36 * page), Main.getPlugin().getFreundeManager().getFreund(player).getFriends().size() - (36 * (page - 1))); i++) {
            try {
                UUID uuid = freunde.getFriends().get(i);
                String name = UUIDFetcher.getName(uuid);
                if (freunde.getOnlineFriends().contains(uuid)) {
                    inv.setItem(slot, new ItemBuilder(skulls.get(uuid)).setLore("§8➥ §e" + BridgePlayerManager.getInstance().getOnlinePlayer(uuid).getConnectedService().getServerName()).setName("§a" + name).build());
                } else {
                    inv.setItem(slot, new ItemBuilder(skulls.get(uuid)).setName("§c" + name).build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            slot++;
        }
        return inv;
    }

    private Inventory getRequestInventory(Player player, int page) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, AGUI_NAME);
        for (int i = 36; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15).setName("§6").build());
        }
        inv.setItem(45, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setTexture("http://textures.minecraft.net/texture/37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645").setName("§7Zurück").build());
        inv.setItem(49, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("§7Seite " + page).setTexture(getTexture(page)).build());
        inv.setItem(53, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setTexture("http://textures.minecraft.net/texture/682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e").setName("§7Vor").build());
        int slot = 0;
        Freunde freunde = Main.getPlugin().getFreundeManager().getFreund(player);
        try {
            for (int i = (36 * (page - 1)); i < Math.min((36 * page), Main.getPlugin().getFreundeManager().getFreund(player).getRequests().size() - (36 * (page - 1))); i++) {
                UUID uuid = freunde.getFriends().get(i);
                String name = UUIDFetcher.getName(uuid);
                inv.setItem(slot, new ItemBuilder(skulls.get(uuid)).setName("§7" + name).build());
                slot++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inv;
    }

    private String getTexture(int page) {
        switch (page) {
            case 1:
                return "http://textures.minecraft.net/texture/d2a6f0e84daefc8b21aa99415b16ed5fdaa6d8dc0c3cd591f49ca832b575";
            case 2:
                return "http://textures.minecraft.net/texture/96fab991d083993cb83e4bcf44a0b6cefac647d4189ee9cb823e9cc1571e38";
            case 3:
                return "http://textures.minecraft.net/texture/cd319b9343f17a35636bcbc26b819625a9333de3736111f2e932827c8e749";
            case 4:
                return "http://textures.minecraft.net/texture/d198d56216156114265973c258f57fc79d246bb65e3c77bbe8312ee35db6";
            case 5:
                return "http://textures.minecraft.net/texture/7fb91bb97749d6a6eed4449d23aea284dc4de6c3818eea5c7e149ddda6f7c9";
            case 6:
                return "http://textures.minecraft.net/texture/9c613f80a554918c7ab2cd4a278752f151412a44a73d7a286d61d45be4eaae1";
            case 7:
                return "http://textures.minecraft.net/texture/9e198fd831cb61f3927f21cf8a7463af5ea3c7e43bd3e8ec7d2948631cce879";
            case 8:
                return "http://textures.minecraft.net/texture/84ad12c2f21a1972f3d2f381ed05a6cc088489fcfdf68a713b387482fe91e2";
            case 9:
                return "http://textures.minecraft.net/texture/9f7aa0d97983cd67dfb67b7d9d9c641bc9aa34d96632f372d26fee19f71f8b7";
            case 10:
                return "http://textures.minecraft.net/texture/b0cf9794fbc089dab037141f67875ab37fadd12f3b92dba7dd2288f1e98836";
            default:
                return "http://textures.minecraft.net/texture/6d68343bd0b129de93cc8d3bba3b97a2faa7ade38d8a6e2b864cd868cfab";
        }
    }

}
