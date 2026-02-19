package dev.blooddev.titanhammer.listeners;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.event.Listener;

public class EntityDeathListener implements Listener {
    private final TitanHammerPro plugin;

    public EntityDeathListener(TitanHammerPro plugin) {
        this.plugin = plugin;
    }
}
