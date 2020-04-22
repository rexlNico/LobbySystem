package de.rexlnico.lobbysystem.methodes;

import de.rexlnico.lobbysystem.freunde.Freunde;
import de.rexlnico.lobbysystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private Scoreboard scoreboard;

    public ScoreboardManager() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewTeam("001Admin");
        scoreboard.getTeam("001Admin").setPrefix("§4Admin §8● §4");
        scoreboard.registerNewTeam("002Developer");
        scoreboard.getTeam("002Developer").setPrefix("§bDev §8● §b");
        scoreboard.registerNewTeam("003Moderator");
        scoreboard.getTeam("003Moderator").setPrefix("§9Mod §8● §1");
        scoreboard.registerNewTeam("004Builder");
        scoreboard.getTeam("004Builder").setPrefix("§eBuilder §8● §e");
        scoreboard.registerNewTeam("005Supporter");
        scoreboard.getTeam("005Supporter").setPrefix("§aSupp §8● §a");
        scoreboard.registerNewTeam("006Youtuber");
        scoreboard.getTeam("006Youtuber").setPrefix("§5");
        scoreboard.registerNewTeam("007Premium");
        scoreboard.getTeam("007Premium").setPrefix("§6");
        scoreboard.registerNewTeam("008Spieler");
        scoreboard.getTeam("008Spieler").setPrefix("§7");

        for (Player a : Bukkit.getOnlinePlayers()) {
            setScoreboard(a);
            setSideScoreboard(a);
        }

    }

    public void setScoreboard(Player player) {
        String team = "";
        if (player.hasPermission(Permissions.RANG_ADMIN.getPermission())) {
            team = "001Admin";
        } else if (player.hasPermission(Permissions.RANG_DEVELOPER.getPermission())) {
            team = "002Developer";
        } else if (player.hasPermission(Permissions.RANG_MODERATOR.getPermission())) {
            team = "003Moderator";
        } else if (player.hasPermission(Permissions.RANG_BUILDER.getPermission())) {
            team = "004Builder";
        } else if (player.hasPermission(Permissions.RANG_SUPPORTER.getPermission())) {
            team = "005Supporter";
        } else if (player.hasPermission(Permissions.RANG_YOUTUBER.getPermission())) {
            team = "006Youtuber";
        } else if (player.hasPermission(Permissions.RANG_PREMIUM.getPermission())) {
            team = "007Premium";
        } else {
            team = "008Spieler";
        }

        scoreboard.getTeam(team).addPlayer(player);
        player.setDisplayName(scoreboard.getTeam(team).getPrefix() + player.getName());

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreboard);
        }
    }

    public void setSideScoreboard(Player player) {

        Freunde freunde = (Main.getPlugin().getFreundeManager() == null ? null : Main.getPlugin().getFreundeManager().getFreund(player));

        ScoreboardApi scoreboardApi = new ScoreboardApi(player, "§2§lr§aexlNico.de §8● §7Lobby");

        scoreboardApi.addLine("§a");
        scoreboardApi.addLine("§b  §a■ §fDein Rang");
        scoreboardApi.addLine("§c     §8§l» " + getRang(player));
        scoreboardApi.addLine("§d");
        scoreboardApi.addLine("§b  §a■ §fDeine Coins");
        scoreboardApi.addLine("§c     §8§l» §7" + ((Main.getPlugin().getCoinManager() == null) ? 0 : Main.getPlugin().getCoinManager().getCoins(player)));
        scoreboardApi.addLine("§e");
        scoreboardApi.addLine("§b  §a■ §fOnline Freunde");
        scoreboardApi.addLine("§c     §8§l» §7" + (freunde != null ? freunde.getOnlineFriends().size() : 0) + "§8/§7" + (freunde != null ? freunde.getFriends().size() : 0));
        scoreboardApi.addLine("§f");
        scoreboardApi.addLine("§b  §a■ §fDiscord");
        scoreboardApi.addLine("§c     §8§l» §7dc.rexlNico.de");
        scoreboardApi.addLine("§1");
        scoreboardApi.sendScoreboard();
    }

    private String getRang(Player player) {
        if (Permissions.hasPermissions(player, Permissions.RANG_ADMIN)) {
            return "§4Admin";
        } else if (Permissions.hasPermissions(player, Permissions.RANG_DEVELOPER)) {
            return "§bDeveloper";
        } else if (Permissions.hasPermissions(player, Permissions.RANG_MODERATOR)) {
            return "§9Moderator";
        } else if (Permissions.hasPermissions(player, Permissions.RANG_BUILDER)) {
            return "§eBuilder";
        } else if (Permissions.hasPermissions(player, Permissions.RANG_SUPPORTER)) {
            return "§aSupporter";
        } else if (Permissions.hasPermissions(player, Permissions.RANG_YOUTUBER)) {
            return "§5Youtuber";
        } else if (Permissions.hasPermissions(player, Permissions.RANG_PREMIUM)) {
            return "§6Premium";
        }
        return "§7Spieler";
    }

}
