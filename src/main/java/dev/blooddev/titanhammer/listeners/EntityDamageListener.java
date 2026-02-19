package dev.blooddev.titanhammer.listeners;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.event.Listener;

public class EntityDamageListener implements Listener {
    private final TitanHammerPro plugin;

    public EntityDamageListener(TitanHammerPro plugin) {
        this.plugin = plugin;
    }
}
