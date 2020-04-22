package de.rexlnico.lobbysystem.teleporter;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TeleportCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.TELEPORTER_SET.getPermission())) {
                if (args.length == 1) {
                    String name = args[0];
                    try {
                        TeleportManager.Teleports teleport = TeleportManager.Teleports.valueOf(name.toUpperCase());
                        File file = teleport.getFile();
                        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        cfg.set("Location", player.getLocation());
                        cfg.save(file);
                        Main.getPlugin().getTeleportManager().update(teleport);
                        player.sendMessage("§aDu hast die Position für den Teleport von " + teleport.toString() + " erfolgreich gesetzt!");
                    } catch (Exception e) {
                        player.sendMessage("§4Es wurde kein Teleport mit dem namen " + name + " gefunden!");
                    }
                } else {
                    player.sendMessage("§4/teleport <Teleport>");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (sender.hasPermission(Permissions.TELEPORTER_SET.getPermission())) {
            if (args.length == 1) {
                for (TeleportManager.Teleports teleports : TeleportManager.Teleports.values()) {
                    list.add(teleports.toString());
                }
            }
        }
        return list;
    }
}
