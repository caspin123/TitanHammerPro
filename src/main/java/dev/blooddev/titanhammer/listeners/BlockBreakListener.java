package dev.blooddev.titanhammer.listeners;

import dev.blooddev.titanhammer.TitanHammerPro;
import dev.blooddev.titanhammer.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BlockBreakListener implements Listener {

    private final TitanHammerPro plugin;
    private final Set<UUID> processingPlayers;
    private final Map<Location, Long> brokenBlocks;

    public BlockBreakListener(TitanHammerPro plugin) {
        this.plugin = plugin;
        this.processingPlayers = new HashSet<>();
        this.brokenBlocks = new HashMap<>();
        
        // Clear old broken blocks every 5 seconds to prevent memory leak
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long currentTime = System.currentTimeMillis();
            brokenBlocks.entrySet().removeIf(entry -> currentTime - entry.getValue() > 5000);
        }, 100L, 100L);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();

        // Check if using Titan Hammer
        if (!isTitanHammer(item)) {
            return;
        }

        // Anti-duplication protection
        if (processingPlayers.contains(player.getUniqueId())) {
            return;
        }

        // Check if block was already broken (anti-duplication)
        if (isRecentlyBroken(block.getLocation())) {
            event.setCancelled(true);
            return;
        }

        PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        // Mark this block as broken
        markBlockBroken(block.getLocation());

        // Handle block filter
        handleBlockFilter(player, playerData, block, event);

        // Add XP
        double xpGain = calculateXPGain(block.getType());
        playerData.addExperience(xpGain);

        // Add statistics
        playerData.addBlocksDestroyed(1);

        // Handle range mining
        if (playerData.getRangeLevel() > 0 && !player.isSneaking()) {
            processingPlayers.add(player.getUniqueId());
            
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                handleRangeMining(player, playerData, block, item);
                processingPlayers.remove(player.getUniqueId());
            }, 1L);
        }

        // Apply efficiency bonus
        applyEfficiencyBonus(player, item, playerData);

        // Particles and sounds
        if (playerData.isParticlesEnabled()) {
            plugin.getParticleManager().playBlockBreakParticles(block.getLocation(), block.getType());
        }
        
        if (playerData.isSoundsEnabled()) {
            plugin.getSoundManager().playBlockBreakSound(player);
        }

        // Apply durability multiplier (reduce damage taken)
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            applyDurabilityBonus(item, playerData);
        }, 2L);
    }

    private void handleRangeMining(Player player, PlayerData playerData, Block centerBlock, ItemStack item) {
        int range = playerData.getBreakRange();
        Location center = centerBlock.getLocation();
        
        List<Block> blocksToBreak = new ArrayList<>();
        
        // Get blocks in range
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    
                    Block block = center.clone().add(x, y, z).getBlock();
                    
                    // Check if block is valid for mining
                    if (isValidMiningBlock(block, centerBlock.getType())) {
                        blocksToBreak.add(block);
                    }
                }
            }
        }

        // Break blocks
        for (Block block : blocksToBreak) {
            if (isRecentlyBroken(block.getLocation())) {
                continue;
            }

            // Simulate break event
            BlockBreakEvent simulatedEvent = new BlockBreakEvent(block, player);
            Bukkit.getPluginManager().callEvent(simulatedEvent);

            if (!simulatedEvent.isCancelled()) {
                markBlockBroken(block.getLocation());
                
                // Handle block filter
                handleBlockFilter(player, playerData, block, simulatedEvent);
                
                // Break block
                if (!simulatedEvent.isDropItems()) {
                    block.setType(Material.AIR, false);
                } else {
                    block.breakNaturally(item);
                }

                // Particles
                if (playerData.isParticlesEnabled()) {
                    plugin.getParticleManager().playBlockBreakParticles(block.getLocation(), block.getType());
                }

                // Statistics
                playerData.addBlocksDestroyed(1);
                
                // XP
                double xpGain = calculateXPGain(block.getType()) * 0.5; // 50% XP for range breaks
                playerData.addExperience(xpGain);
            }
        }

        // Apply extra durability damage for range mining
        if (item.getItemMeta() instanceof Damageable) {
            Damageable damageable = (Damageable) item.getItemMeta();
            int extraDamage = (int) Math.ceil(blocksToBreak.size() / 2.0);
            damageable.setDamage(damageable.getDamage() + extraDamage);
            item.setItemMeta((ItemMeta) damageable);
        }
    }

    private void handleBlockFilter(Player player, PlayerData playerData, Block block, BlockBreakEvent event) {
        Material material = block.getType();

        if (playerData.shouldAutoDelete(material)) {
            // Auto delete mode - remove drops
            event.setDropItems(false);
            event.setExpToDrop(0);
        } else if (playerData.shouldAutoCollect(material)) {
            // Auto collect mode - add to inventory
            event.setDropItems(false);
            
            Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
            
            // Apply fortune bonus
            if (playerData.getFortuneBonus() > 0) {
                drops = applyFortuneBonus(drops, playerData.getFortuneBonus(), material);
            }
            
            // Add to inventory or drop if full
            for (ItemStack drop : drops) {
                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(drop);
                if (!leftover.isEmpty()) {
                    for (ItemStack item : leftover.values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), item);
                    }
                }
            }
        }
    }

    private Collection<ItemStack> applyFortuneBonus(Collection<ItemStack> drops, int fortuneLevel, Material material) {
        // Fortune only affects certain blocks
        if (!isFortuneAffected(material)) {
            return drops;
        }

        List<ItemStack> enhancedDrops = new ArrayList<>();
        Random random = new Random();

        for (ItemStack drop : drops) {
            int amount = drop.getAmount();
            
            // Fortune calculation
            int bonus = random.nextInt(fortuneLevel + 1);
            amount += bonus;
            
            drop.setAmount(amount);
            enhancedDrops.add(drop);
        }

        return enhancedDrops;
    }

    private boolean isFortuneAffected(Material material) {
        return material == Material.DIAMOND_ORE ||
               material == Material.DEEPSLATE_DIAMOND_ORE ||
               material == Material.EMERALD_ORE ||
               material == Material.DEEPSLATE_EMERALD_ORE ||
               material == Material.COAL_ORE ||
               material == Material.DEEPSLATE_COAL_ORE ||
               material == Material.LAPIS_ORE ||
               material == Material.DEEPSLATE_LAPIS_ORE ||
               material == Material.REDSTONE_ORE ||
               material == Material.DEEPSLATE_REDSTONE_ORE ||
               material == Material.NETHER_QUARTZ_ORE ||
               material == Material.NETHER_GOLD_ORE;
    }

    private void applyEfficiencyBonus(Player player, ItemStack item, PlayerData playerData) {
        int efficiencyBonus = playerData.getEfficiencyBonus();
        
        if (efficiencyBonus > 0) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                int currentLevel = meta.getEnchantLevel(Enchantment.EFFICIENCY);
                // Temporary boost (doesn't save to item)
                player.addPotionEffect(new org.bukkit.potion.PotionEffect(
                    org.bukkit.potion.PotionEffectType.HASTE, 
                    20, 
                    efficiencyBonus - 1, 
                    false, 
                    false
                ));
            }
        }
    }

    private void applyDurabilityBonus(ItemStack item, PlayerData playerData) {
        if (item.getItemMeta() instanceof Damageable) {
            Damageable damageable = (Damageable) item.getItemMeta();
            int currentDamage = damageable.getDamage();
            
            // Reduce damage based on durability multiplier
            double multiplier = playerData.getDurabilityMultiplier();
            if (multiplier > 1.0 && currentDamage > 0) {
                // Chance to not take durability damage
                Random random = new Random();
                double chance = (multiplier - 1.0) * 100; // Convert to percentage
                
                if (random.nextDouble() * 100 < chance) {
                    damageable.setDamage(Math.max(0, currentDamage - 1));
                    item.setItemMeta((ItemMeta) damageable);
                }
            }
        }
    }

    private boolean isValidMiningBlock(Block block, Material centerType) {
        if (block.getType() == Material.AIR) {
            return false;
        }

        if (block.getType() == Material.BEDROCK) {
            return false;
        }

        // Check if block is similar type (e.g., all stone types together)
        return isSimilarBlock(block.getType(), centerType);
    }

    private boolean isSimilarBlock(Material block1, Material block2) {
        // Same type
        if (block1 == block2) {
            return true;
        }

        // Stone types
        Set<Material> stoneTypes = Set.of(
            Material.STONE, Material.ANDESITE, Material.DIORITE, 
            Material.GRANITE, Material.DEEPSLATE, Material.TUFF
        );
        
        if (stoneTypes.contains(block1) && stoneTypes.contains(block2)) {
            return true;
        }

        // Ore types - only mine same ore
        return false;
    }

    private double calculateXPGain(Material material) {
        return switch (material) {
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> 50.0;
            case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> 40.0;
            case GOLD_ORE, DEEPSLATE_GOLD_ORE -> 30.0;
            case IRON_ORE, DEEPSLATE_IRON_ORE -> 20.0;
            case COAL_ORE, DEEPSLATE_COAL_ORE -> 10.0;
            case ANCIENT_DEBRIS -> 100.0;
            case NETHERITE_BLOCK -> 150.0;
            case OBSIDIAN -> 25.0;
            default -> 5.0;
        };
    }

    private boolean isTitanHammer(ItemStack item) {
        if (item == null || item.getType() != Material.NETHERITE_AXE) {
            return false;
        }

        if (!item.hasItemMeta()) {
            return false;
        }

        if (item.getItemMeta().hasCustomModelData()) {
            return item.getItemMeta().getCustomModelData() == 2025;
        }

        return false;
    }

    private boolean isRecentlyBroken(Location location) {
        return brokenBlocks.containsKey(location);
    }

    private void markBlockBroken(Location location) {
        brokenBlocks.put(location, System.currentTimeMillis());
    }
}
