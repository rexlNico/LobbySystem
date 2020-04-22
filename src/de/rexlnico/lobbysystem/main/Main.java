package de.rexlnico.lobbysystem.main;

import de.rexlnico.lobbysystem.building.BuildCommand;
import de.rexlnico.lobbysystem.building.BuildManager;
import de.rexlnico.lobbysystem.dailyrewards.DailyRewardCommand;
import de.rexlnico.lobbysystem.dailyrewards.DailyRewardListeners;
import de.rexlnico.lobbysystem.freunde.FreundeGui;
import de.rexlnico.lobbysystem.freunde.FreundeManager;
import de.rexlnico.lobbysystem.listeners.Chat;
import de.rexlnico.lobbysystem.listeners.Damage;
import de.rexlnico.lobbysystem.listeners.Join;
import de.rexlnico.lobbysystem.coins.CoinManager;
import de.rexlnico.lobbysystem.lobbywechsler.LobbyWechsler;
import de.rexlnico.lobbysystem.methodes.MySQL;
import de.rexlnico.lobbysystem.nick.NickManager;
import de.rexlnico.lobbysystem.methodes.ScoreboardManager;
import de.rexlnico.lobbysystem.teleporter.TeleportCommand;
import de.rexlnico.lobbysystem.teleporter.TeleportManager;
import de.rexlnico.lobbysystem.teleporter.UseTeleporter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private PluginManager pm;
    private MySQL mySQL;
    private ScoreboardManager scoreboardManager;
    private TeleportManager teleportManager;
    private NickManager nickManager;
    private BuildManager buildManager;
    private CoinManager coinManager;
    private FreundeManager freundeManager;

    @Override
    public void onEnable() {
        plugin = this;
        pm = Bukkit.getPluginManager();

        try {
            mySQL = new MySQL("localhost", 3306, "Netzwerk", "Tigm4CyayGeX8rfh", "Netzwerk");

            mySQL.queryUpdate("CREATE TABLE IF NOT EXISTS Nickuser(UUID varchar(36), Aktive varchar(5))");
            mySQL.queryUpdate("CREATE TABLE IF NOT EXISTS Coinmanager(UUID varchar(36), Coins int(10))");
            mySQL.queryUpdate("CREATE TABLE IF NOT EXISTS Freunde(UUID varchar(36), Freunde varchar(255), Requests varchar(255))");
            mySQL.queryUpdate("CREATE TABLE IF NOT EXISTS DailyRewards(UUID varchar(36), CoinsNormal varchar(255), CoinsPremium varchar(255))");

        } catch (Exception e) {
            e.printStackTrace();
        }

        teleportManager = new TeleportManager();
        nickManager = new NickManager();
        pm.registerEvents(nickManager, this);
        buildManager = new BuildManager();
        pm.registerEvents(buildManager, this);
        coinManager = new CoinManager();
        pm.registerEvents(coinManager, this);
        freundeManager = new FreundeManager();
        pm.registerEvents(freundeManager, this);
        scoreboardManager = new ScoreboardManager();
        pm.registerEvents(new LobbyWechsler(), this);

        pm.registerEvents(new FreundeGui(), this);
        pm.registerEvents(new UseTeleporter(), this);
        pm.registerEvents(new Damage(), this);
        pm.registerEvents(new Join(), this);
        pm.registerEvents(new Chat(), this);
        pm.registerEvents(new DailyRewardListeners(), this);

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("dailyrewards").setExecutor(new DailyRewardCommand());

        Bukkit.getConsoleSender().sendMessage("§4Lobbysytem §ageladen!");
    }

    @Override
    public void onDisable() {
        nickManager.save();
        coinManager.save();
    }

    public static Main getPlugin() {
        return plugin;
    }

    public NickManager getNickManager() {
        return nickManager;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public FreundeManager getFreundeManager() {
        return freundeManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public CoinManager getCoinManager() {
        return coinManager;
    }

    public BuildManager getBuildManager() {
        return buildManager;
    }
}