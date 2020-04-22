package de.rexlnico.lobbysystem.methodes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL
{
    private String host;
    private String user;
    private String password;
    private String database;
    private int port;
    private Connection conn;

    public MySQL(final String host, final int port, final String user, final String password, final String database) throws Exception {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
        this.openConnection();
    }

    public void queryUpdate(final String query) {
        this.checkConnection();
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            this.queryUpdate(statement);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryUpdate(final PreparedStatement statement) {
        this.checkConnection();
        try {
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                statement.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        finally {
            try {
                statement.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public ResultSet query(final String query) {
        this.checkConnection();
        try {
            return this.query(this.conn.prepareStatement(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet query(final PreparedStatement statement) {
        this.checkConnection();
        try {
            return statement.executeQuery();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void checkConnection() {
        try {
            if (this.conn == null || !this.conn.isValid(10) || this.conn.isClosed()) {
                this.openConnection();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
      //  jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password
    }

    public void closeConnection() {
        try {
            this.conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.conn = null;
        }
    }
}
