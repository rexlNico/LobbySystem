package de.rexlnico.lobbysystem.dailyrewards;

import de.rexlnico.lobbysystem.methodes.Permissions;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class DailyRewardCommand implements CommandExecutor {

    public static final String NAME = "§bTägliche Belohnung";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.DAILYREWARD_SET.getPermission())) {
                if (args.length == 0) {
                    Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
                    location.setPitch(player.getLocation().getPitch());
                    location.setYaw(player.getLocation().getYaw());
                    for (Entity entity : location.getWorld().getEntities()) {
                        if (entity instanceof Villager) {
                            if (entity.getCustomName().equals(NAME)) {
                                entity.remove();
                            }
                        }
                    }
                    Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
                    villager.setProfession(Villager.Profession.PRIEST);
                    villager.setAgeLock(true);
                    villager.setAdult();
                    villager.setCanPickupItems(false);
                    villager.setBreed(false);
                    villager.setCustomNameVisible(true);
                    villager.setCustomName(NAME);
                    setNoAI(villager);
                    player.sendMessage("§aDu hast den Villager für die Täglichen Belohnungen gesetzt.");
                }
            }
        }
        return false;
    }

    private void setNoAI(org.bukkit.entity.Entity bukkitEntity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }

}
