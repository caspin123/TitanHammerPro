package dev.blooddev.titanhammer.listeners;

import dev.blooddev.titanhammer.TitanHammerPro;
import dev.blooddev.titanhammer.gui.MainGUI;
import dev.blooddev.titanhammer.models.PlayerData;
import dev.blooddev.titanhammer.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final TitanHammerPro plugin;

    public PlayerInteractListener(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if player is holding Titan Hammer
        if (!isTitanHammer(item)) {
            return;
        }

        // Check for Shift + Right Click
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                event.setCancelled(true);
                
                // Open Main GUI
                PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
                MainGUI mainGUI = new MainGUI(plugin, player, playerData);
                mainGUI.open();
                
                // Play sound
                plugin.getSoundManager().playOpenGUISound(player);
            }
        }
    }

    private boolean isTitanHammer(ItemStack item) {
        if (item == null || item.getType() != Material.NETHERITE_AXE) {
            return false;
        }

        if (!item.hasItemMeta()) {
            return false;
        }

        // Check CustomModelData
        if (item.getItemMeta().hasCustomModelData()) {
            return item.getItemMeta().getCustomModelData() == 2025;
        }

        return false;
    }
}
