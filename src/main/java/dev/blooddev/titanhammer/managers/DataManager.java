package dev.blooddev.titanhammer.managers;

import dev.blooddev.titanhammer.TitanHammerPro;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataManager {

    private final TitanHammerPro plugin;
    private HikariDataSource dataSource;

    public DataManager(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        String dbType = plugin.getConfigManager().getConfig().getString("database.type", "SQLITE");

        if (dbType.equalsIgnoreCase("MYSQL")) {
            setupMySQL();
        } else {
            setupSQLite();
        }

        createTables();
    }

    private void setupSQLite() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + "/data.db");
        config.setMaximumPoolSize(5);
        dataSource = new HikariDataSource(config);
        plugin.getLogger().info("SQLite database connected!");
    }

    private void setupMySQL() {
        HikariConfig config = new HikariConfig();
        String host = plugin.getConfigManager().getConfig().getString("database.mysql.host");
        int port = plugin.getConfigManager().getConfig().getInt("database.mysql.port");
        String database = plugin.getConfigManager().getConfig().getString("database.mysql.database");
        String username = plugin.getConfigManager().getConfig().getString("database.mysql.username");
        String password = plugin.getConfigManager().getConfig().getString("database.mysql.password");

        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
        plugin.getLogger().info("MySQL database connected!");
    }

    private void createTables() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS titan_players (" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "player_name VARCHAR(16)," +
                    "level INT DEFAULT 1," +
                    "experience DOUBLE DEFAULT 0," +
                    "skill_points INT DEFAULT 0," +
                    "damage_level INT DEFAULT 0," +
                    "efficiency_level INT DEFAULT 0," +
                    "fortune_level INT DEFAULT 0," +
                    "durability_level INT DEFAULT 0," +
                    "range_level INT DEFAULT 0," +
                    "filter_mode VARCHAR(20) DEFAULT 'NONE'," +
                    "blocks_destroyed BIGINT DEFAULT 0," +
                    "mobs_killed BIGINT DEFAULT 0," +
                    "total_damage DOUBLE DEFAULT 0," +
                    "particles_enabled BOOLEAN DEFAULT TRUE," +
                    "sounds_enabled BOOLEAN DEFAULT TRUE," +
                    "language VARCHAR(5) DEFAULT 'en'" +
                    ")");
            plugin.getLogger().info("Database tables created successfully!");
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to create tables: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
