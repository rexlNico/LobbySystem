package de.rexlnico.lobbysystem.building;

import de.rexlnico.lobbysystem.listeners.Join;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.Permissions;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.BaseComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.BUILD.getPermission())) {
                if (args.length == 0) {
                    boolean after = !Main.getPlugin().getBuildManager().canBuild(player);
                    Main.getPlugin().getBuildManager().setState(player, after);
                    if (after) {
                        sendActionBar(player, "§aDu kanst nun bauen!");
                        player.setGameMode(GameMode.CREATIVE);
                        player.getInventory().clear();
                    } else {
                        sendActionBar(player, "§cDu kanst nun nicht mehr bauen!");
                        player.setGameMode(GameMode.SURVIVAL);
                        player.getInventory().clear();
                        Join.giveJoinItems(player);
                    }
                }
            }
        }
        return false;
    }

    public static void sendActionBar(Player player, String string) {
        final String NachrichtNeu = string.replace("_", " ");
        String s = ChatColor.translateAlternateColorCodes('&', NachrichtNeu);
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }

}
