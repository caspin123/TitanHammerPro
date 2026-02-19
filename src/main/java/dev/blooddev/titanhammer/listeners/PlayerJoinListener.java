package dev.blooddev.titanhammer.listeners;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final TitanHammerPro plugin;

    public PlayerJoinListener(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getPlayerDataManager().getPlayerData(event.getPlayer().getUniqueId());
    }
}
