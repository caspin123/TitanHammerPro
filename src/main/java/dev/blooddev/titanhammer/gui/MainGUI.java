package dev.blooddev.titanhammer.gui;

import dev.blooddev.titanhammer.TitanHammerPro;
import dev.blooddev.titanhammer.models.PlayerData;
import dev.blooddev.titanhammer.utils.ItemBuilder;
import dev.blooddev.titanhammer.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MainGUI {

    private final TitanHammerPro plugin;
    private final Player player;
    private final PlayerData playerData;
    private final Inventory inventory;

    public MainGUI(TitanHammerPro plugin, Player player, PlayerData playerData) {
        this.plugin = plugin;
        this.player = player;
        this.playerData = playerData;
        this.inventory = Bukkit.createInventory(null, 54, MessageUtil.color("&6&l⚒ Titan Hammer Pro ⚒"));
        
        setupGUI();
    }

    private void setupGUI() {
        // Fill borders
        fillBorders();
        
        // Player Info
        inventory.setItem(4, createPlayerInfoItem());
        
        // Main Menu Items
        inventory.setItem(20, createSkillTreeItem());
        inventory.setItem(22, createUpgradeItem());
        inventory.setItem(24, createBlockFilterItem());
        inventory.setItem(29, createStatisticsItem());
        inventory.setItem(31, createSettingsItem());
        inventory.setItem(33, createShopItem());
        
        // Close button
        inventory.setItem(49, createCloseItem());
    }

    private void fillBorders() {
        ItemStack glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName(" ")
                .build();

        int[] borders = {0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52, 53};
        for (int slot : borders) {
            inventory.setItem(slot, glass);
        }
    }

    private ItemStack createPlayerInfoItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName(MessageUtil.getMessage("gui.main.player-info.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.player-info.level", lang) + " &e" + playerData.getLevel(),
                        MessageUtil.getMessage("gui.main.player-info.xp", lang) + " &b" + String.format("%.1f", playerData.getExperience()) + "&7/&b" + String.format("%.0f", playerData.getRequiredExperience()),
                        MessageUtil.getMessage("gui.main.player-info.progress", lang) + " &a" + String.format("%.1f", playerData.getExperienceProgress()) + "%",
                        "",
                        MessageUtil.getMessage("gui.main.player-info.skill-points", lang) + " &d" + playerData.getSkillPoints()
                ))
                .build();
    }

    private ItemStack createSkillTreeItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.ENCHANTED_BOOK)
                .setName(MessageUtil.getMessage("gui.main.skill-tree.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.skill-tree.desc", lang),
                        "",
                        MessageUtil.getMessage("gui.main.skill-tree.available", lang) + " &d" + playerData.getSkillPoints(),
                        "",
                        MessageUtil.getMessage("gui.main.click-to-open", lang)
                ))
                .addGlow()
                .build();
    }

    private ItemStack createUpgradeItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.ANVIL)
                .setName(MessageUtil.getMessage("gui.main.upgrades.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.upgrades.desc", lang),
                        "",
                        MessageUtil.getMessage("gui.main.upgrades.damage", lang) + " &c" + playerData.getDamageLevel() + "&7/&c10",
                        MessageUtil.getMessage("gui.main.upgrades.efficiency", lang) + " &e" + playerData.getEfficiencyLevel() + "&7/&e10",
                        MessageUtil.getMessage("gui.main.upgrades.fortune", lang) + " &a" + playerData.getFortuneLevel() + "&7/&a5",
                        MessageUtil.getMessage("gui.main.upgrades.durability", lang) + " &b" + playerData.getDurabilityLevel() + "&7/&b10",
                        MessageUtil.getMessage("gui.main.upgrades.range", lang) + " &d" + playerData.getRangeLevel() + "&7/&d5",
                        "",
                        MessageUtil.getMessage("gui.main.click-to-open", lang)
                ))
                .addGlow()
                .build();
    }

    private ItemStack createBlockFilterItem() {
        String lang = playerData.getLanguage();
        String modeKey = "gui.main.filter.mode." + playerData.getFilterMode().name().toLowerCase();
        
        Material icon = switch (playerData.getFilterMode()) {
            case AUTO_COLLECT -> Material.CHEST;
            case AUTO_DELETE -> Material.LAVA_BUCKET;
            default -> Material.HOPPER;
        };
        
        return new ItemBuilder(icon)
                .setName(MessageUtil.getMessage("gui.main.filter.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.filter.desc", lang),
                        "",
                        MessageUtil.getMessage("gui.main.filter.current-mode", lang) + " " + MessageUtil.getMessage(modeKey, lang),
                        MessageUtil.getMessage("gui.main.filter.blocks-filtered", lang) + " &e" + playerData.getFilteredBlocks().size(),
                        "",
                        MessageUtil.getMessage("gui.main.click-to-open", lang)
                ))
                .addGlow()
                .build();
    }

    private ItemStack createStatisticsItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.BOOK)
                .setName(MessageUtil.getMessage("gui.main.stats.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.stats.blocks", lang) + " &e" + playerData.getBlocksDestroyed(),
                        MessageUtil.getMessage("gui.main.stats.mobs", lang) + " &c" + playerData.getMobsKilled(),
                        MessageUtil.getMessage("gui.main.stats.damage", lang) + " &6" + String.format("%.1f", playerData.getTotalDamage()),
                        "",
                        MessageUtil.getMessage("gui.main.click-to-open", lang)
                ))
                .build();
    }

    private ItemStack createSettingsItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.COMPARATOR)
                .setName(MessageUtil.getMessage("gui.main.settings.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.settings.particles", lang) + " " + (playerData.isParticlesEnabled() ? "&a✓" : "&c✗"),
                        MessageUtil.getMessage("gui.main.settings.sounds", lang) + " " + (playerData.isSoundsEnabled() ? "&a✓" : "&c✗"),
                        MessageUtil.getMessage("gui.main.settings.language", lang) + " &e" + playerData.getLanguage().toUpperCase(),
                        "",
                        MessageUtil.getMessage("gui.main.click-to-open", lang)
                ))
                .build();
    }

    private ItemStack createShopItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.EMERALD)
                .setName(MessageUtil.getMessage("gui.main.shop.title", lang))
                .setLore(Arrays.asList(
                        MessageUtil.getMessage("gui.main.shop.desc", lang),
                        "",
                        MessageUtil.getMessage("gui.main.click-to-open", lang)
                ))
                .addGlow()
                .build();
    }

    private ItemStack createCloseItem() {
        String lang = playerData.getLanguage();
        
        return new ItemBuilder(Material.BARRIER)
                .setName(MessageUtil.getMessage("gui.close", lang))
                .build();
    }

    public void open() {
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
