package de.rexlnico.lobbysystem.methodes;

import org.bukkit.entity.Player;

public enum Permissions {

    BUILD("Build.build"),
    TELEPORTER_SET("Teleporter.set"),
    TELEPORTER_USE("Teleporter.use"),
    CHAT_COLOR("Chat.color"),
    DAILYREWARD_SET("DailyReward.set"),

    RANG_ADMIN("Rang.admin"),
    RANG_DEVELOPER("Rang.developer"),
    RANG_MODERATOR("Rang.moderator"),
    RANG_BUILDER("Rang.builder"),
    RANG_SUPPORTER("Rang.supporter"),
    RANG_YOUTUBER("Rang.youtuber"),
    RANG_PREMIUM("Rang.premium");

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static boolean hasPermissions(Player player, Permissions... permissions) {
        Permissions[] perms = permissions;
        for (Permissions permission : perms) {
            if (player.hasPermission(permission.getPermission())) {
                return true;
            }
        }
        return false;
    }
}
