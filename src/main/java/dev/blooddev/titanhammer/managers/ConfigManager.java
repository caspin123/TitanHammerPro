package dev.blooddev.titanhammer.managers;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {

    private final TitanHammerPro plugin;
    private FileConfiguration config;
    private FileConfiguration messagesEn;
    private FileConfiguration messagesAr;

    public ConfigManager(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        // Save default config files
        plugin.saveDefaultConfig();
        saveResource("messages_en.yml");
        saveResource("messages_ar.yml");

        // Load configurations
        config = plugin.getConfig();
        messagesEn = loadConfig("messages_en.yml");
        messagesAr = loadConfig("messages_ar.yml");

        plugin.getLogger().info("Configurations loaded successfully!");
    }

    private FileConfiguration loadConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(file);
    }

    private void saveResource(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        messagesEn = loadConfig("messages_en.yml");
        messagesAr = loadConfig("messages_ar.yml");
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages(String language) {
        return language.equalsIgnoreCase("ar") ? messagesAr : messagesEn;
    }
}
