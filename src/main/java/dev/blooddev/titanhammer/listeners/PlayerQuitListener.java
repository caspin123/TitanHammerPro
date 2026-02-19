package dev.blooddev.titanhammer.listeners;

import dev.blooddev.titanhammer.TitanHammerPro;
import dev.blooddev.titanhammer.models.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final TitanHammerPro plugin;

    public PlayerQuitListener(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData data = plugin.getPlayerDataManager().getPlayerData(event.getPlayer().getUniqueId());
        plugin.getPlayerDataManager().savePlayerData(data);
        plugin.getPlayerDataManager().removeFromCache(event.getPlayer().getUniqueId());
    }
}
