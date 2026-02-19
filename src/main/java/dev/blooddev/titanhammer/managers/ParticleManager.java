package dev.blooddev.titanhammer.managers;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public class ParticleManager {
    private final TitanHammerPro plugin;

    public ParticleManager(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    public void playBlockBreakParticles(Location location, Material material) {
        location.getWorld().spawnParticle(Particle.EXPLOSION, location, 10, 0.5, 0.5, 0.5, 0.1);
    }
}
