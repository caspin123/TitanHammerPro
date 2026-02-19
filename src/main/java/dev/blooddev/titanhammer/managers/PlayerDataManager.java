package dev.blooddev.titanhammer.managers;

import dev.blooddev.titanhammer.TitanHammerPro;
import dev.blooddev.titanhammer.models.PlayerData;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final TitanHammerPro plugin;
    private final Map<UUID, PlayerData> cache;

    public PlayerDataManager(TitanHammerPro plugin) {
        this.plugin = plugin;
        this.cache = new HashMap<>();
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        PlayerData data = loadPlayerData(uuid);
        cache.put(uuid, data);
        return data;
    }

    private PlayerData loadPlayerData(UUID uuid) {
        try (Connection conn = plugin.getDataManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM titan_players WHERE uuid = ?")) {
            
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PlayerData data = new PlayerData(uuid);
                data.setPlayerName(rs.getString("player_name"));
                data.setLevel(rs.getInt("level"));
                data.setExperience(rs.getDouble("experience"));
                data.setSkillPoints(rs.getInt("skill_points"));
                data.setDamageLevel(rs.getInt("damage_level"));
                data.setEfficiencyLevel(rs.getInt("efficiency_level"));
                data.setFortuneLevel(rs.getInt("fortune_level"));
                data.setDurabilityLevel(rs.getInt("durability_level"));
                data.setRangeLevel(rs.getInt("range_level"));
                data.setFilterMode(PlayerData.FilterMode.valueOf(rs.getString("filter_mode")));
                data.setBlocksDestroyed(rs.getLong("blocks_destroyed"));
                data.setMobsKilled(rs.getLong("mobs_killed"));
                data.setTotalDamage(rs.getDouble("total_damage"));
                data.setParticlesEnabled(rs.getBoolean("particles_enabled"));
                data.setSoundsEnabled(rs.getBoolean("sounds_enabled"));
                data.setLanguage(rs.getString("language"));
                return data;
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load player data: " + e.getMessage());
        }

        return new PlayerData(uuid);
    }

    public void savePlayerData(PlayerData data) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection conn = plugin.getDataManager().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "REPLACE INTO titan_players (uuid, player_name, level, experience, skill_points, " +
                         "damage_level, efficiency_level, fortune_level, durability_level, range_level, " +
                         "filter_mode, blocks_destroyed, mobs_killed, total_damage, particles_enabled, " +
                         "sounds_enabled, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, data.getUuid().toString());
                stmt.setString(2, data.getPlayerName());
                stmt.setInt(3, data.getLevel());
                stmt.setDouble(4, data.getExperience());
                stmt.setInt(5, data.getSkillPoints());
                stmt.setInt(6, data.getDamageLevel());
                stmt.setInt(7, data.getEfficiencyLevel());
                stmt.setInt(8, data.getFortuneLevel());
                stmt.setInt(9, data.getDurabilityLevel());
                stmt.setInt(10, data.getRangeLevel());
                stmt.setString(11, data.getFilterMode().name());
                stmt.setLong(12, data.getBlocksDestroyed());
                stmt.setLong(13, data.getMobsKilled());
                stmt.setDouble(14, data.getTotalDamage());
                stmt.setBoolean(15, data.isParticlesEnabled());
                stmt.setBoolean(16, data.isSoundsEnabled());
                stmt.setString(17, data.getLanguage());
                stmt.executeUpdate();
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to save player data: " + e.getMessage());
            }
        });
    }

    public void saveAllPlayerData() {
        for (PlayerData data : cache.values()) {
            savePlayerData(data);
        }
    }

    public void removeFromCache(UUID uuid) {
        cache.remove(uuid);
    }
}
