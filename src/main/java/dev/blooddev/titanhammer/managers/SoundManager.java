package dev.blooddev.titanhammer.managers;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {
    private final TitanHammerPro plugin;

    public SoundManager(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    public void playOpenGUISound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 1.0f);
    }

    public void playBlockBreakSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 0.8f, 1.2f);
    }
}
