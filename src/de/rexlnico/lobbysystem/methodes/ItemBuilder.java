package de.rexlnico.lobbysystem.methodes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;


public class ItemBuilder {
    private ItemStack item;

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material, 1);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder(int id, int amount) {
        this.item = new ItemStack(id, amount);
    }

    public ItemBuilder(Material material, int amount, int data) {
        /*  30 */
        this.item = new ItemStack(material, amount, (short) data);
    }

    public ItemBuilder enchantIftrue(Boolean b) {
        if (b) {
            enchant(Enchantment.ARROW_DAMAGE, 1);
            addFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder(int id, int amount, int data) {
        /*  30 */
        this.item = new ItemStack(id, amount, (short) data);
    }


    public static void fillInv(Inventory inv, Material material) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, new ItemStack(material));
        }
    }

    public static void setBuyInv3x9(Inventory inv, int cost) {
        fillInv(inv);
        inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(9 + 1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(9 + 2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(9 + 9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(9 + 9 + 1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(9 + 9 + 2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§7Für §a" + cost + " §7kaufen!").build());
        inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 9 + 6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 9 + 7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 9 + 8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
    }

    public static void setSicherInv3x9(Inventory inv) {
        fillInv(inv);
        inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(9 + 1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(9 + 2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(9 + 9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(9 + 9 + 1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(9 + 9 + 2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 5).setName("§aSicher").build());
        inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 9 + 6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 9 + 7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
        inv.setItem(9 + 9 + 8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setName("§cAbbrechen").build());
    }

    @SuppressWarnings("deprecation")
    public static void fillInv(Inventory inv, int material) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, new ItemStack(material));
        }
    }

    public static void fillInv(Inventory inv, ItemStack material) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, material);
        }
    }

    public static void fillInv(Inventory inv) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15).setName("§8").build());
        }
    }

    public ItemBuilder setTexture(String url) {
        ItemStack head = this.item;
        if (head.getType() != Material.SKULL_ITEM) return this;
        if (url.isEmpty()) return this;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        this.item = head;
        return this;
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder setData(int data) {
        this.item.setDurability((short) data);
        return this;
    }

    public ItemBuilder setMaterial(Material m) {
        this.item.setType(m);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta m = this.item.getItemMeta();
        m.setDisplayName(name);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        String[] loreS = lore.split("//");
        ItemMeta m = this.item.getItemMeta();
        m.setLore(Arrays.asList(loreS));
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta m = this.item.getItemMeta();
        m.setLore(lore);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int lvl) {
        this.item.addUnsafeEnchantment(ench, lvl);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flag) {
        ItemMeta m = this.item.getItemMeta();
        m.addItemFlags(flag);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        LeatherArmorMeta m = (LeatherArmorMeta) this.item.getItemMeta();
        m.setColor(color);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta m = (SkullMeta) this.item.getItemMeta();
        m.setOwner(owner);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder enchant() {
        enchant(Enchantment.KNOCKBACK, 1);
        addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setPotionType(PotionEffectType type) {
        PotionMeta m = (PotionMeta) this.item.getItemMeta();
        m.setMainEffect(type);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookAuthor(String author) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setAuthor(author);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookContent(String... pages) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setPages(pages);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookTitle(String title) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setTitle(title);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookMeta(String title, String author, String... pages) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setTitle(title);
        m.setAuthor(author);
        m.setPages(pages);
        this.item.setItemMeta(m);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setEggType(EntityType type) {
        if ((this.item != null) && (this.item.getType() == Material.MONSTER_EGG) && (type != null) && (type.getName() != null)) {
            try {
                String version = org.bukkit.Bukkit.getServer().getClass().toString().split("\\.")[3];
                Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
                Object nmsItemStack = craftItemStack.getDeclaredMethod("asNMSCopy", new Class[]{ItemStack.class}).invoke(null, new Object[]{this.item});
                Object nbtTagCompound = Class.forName("net.minecraft.server." + version + ".NBTTagCompound").newInstance();
                Field nbtTagCompoundField = nmsItemStack.getClass().getDeclaredField("tag");
                nbtTagCompoundField.setAccessible(true);
                nbtTagCompound.getClass().getMethod("setString", new Class[]{String.class, String.class}).invoke(nbtTagCompound, new Object[]{"id", type.getName()});
                nbtTagCompound.getClass().getMethod("set", new Class[]{String.class, Class.forName("net.minecraft.server." + version + ".NBTBase")}).invoke(nbtTagCompoundField.get(nmsItemStack), new Object[]{"EntityTag", nbtTagCompound});
                this.item = ((ItemStack) craftItemStack.getDeclaredMethod("asCraftMirror", new Class[]{nmsItemStack.getClass()}).invoke(null, new Object[]{nmsItemStack}));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    public ItemBuilder setSkullTexture(String base64) {
        ItemMeta m = this.item.getItemMeta();
        GameProfile profile = new GameProfile(java.util.UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));
        Field profileField = null;
        try {
            profileField = m.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(m, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        this.item.setItemMeta(m);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }
}
