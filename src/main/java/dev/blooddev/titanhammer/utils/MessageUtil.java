package dev.blooddev.titanhammer.utils;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.ChatColor;

public class MessageUtil {
    
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getMessage(String path, String language) {
        return color(TitanHammerPro.getInstance()
                .getConfigManager()
                .getMessages(language)
                .getString(path, path));
    }
}
