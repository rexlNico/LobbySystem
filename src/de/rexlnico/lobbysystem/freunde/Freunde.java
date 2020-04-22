package de.rexlnico.lobbysystem.freunde;

import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.UUIDFetcher;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class Freunde {

    private Player player;
    private ArrayList<UUID> friends;
    private ArrayList<UUID> requests;

    public Freunde(Player player) {
        this.player = player;
        update();
    }

    public void update() {
        friends = new ArrayList<>();
        requests = new ArrayList<>();
        if (existUser(player)) {
            ResultSet res = Main.getPlugin().getMySQL().query("SELECT * FROM Freunde WHERE UUID = '" + UUIDFetcher.getUUID(player) + "'");
            try {
                if (res.next()) {
                    String friendsS = res.getString("Freunde");
                    String requestsS = res.getString("Requests");
                    String[] friendList = friendsS.replaceFirst(";", "").split(";");
                    for (String fs : friendList) {
                        friends.add(UUID.fromString(fs));
                    }
                    String[] requestList = requestsS.replaceFirst(";", "").split(";");
                    for (String rs : requestList) {
                        requests.add(UUID.fromString(rs));
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public String getServerFromFriend(UUID uuid) {
        if (getOnlineFriends().contains(uuid)) {
            return BridgePlayerManager.getInstance().getOnlinePlayer(uuid).getConnectedService().getServerName();
        }
        return null;
    }

    public ArrayList<UUID> getFriends() {
        return friends;
    }

    public ArrayList<UUID> getRequests() {
        return requests;
    }

    public ArrayList<UUID> getOnlineFriends() {
        ArrayList<UUID> list = new ArrayList<>();
        for (UUID uuid : friends) {
            if (BridgePlayerManager.getInstance().getOnlinePlayer(uuid) != null) {
                list.add(uuid);
            }
        }
        return list;
    }

    private boolean existUser(Player player) {
        ResultSet res = Main.getPlugin().getMySQL().query("SELECT * FROM Freunde WHERE UUID = '" + UUIDFetcher.getUUID(player) + "'");
        try {
            if (res.next()) {
                return true;
            }
        } catch (Exception e2) {
        }
        return false;
    }

}
