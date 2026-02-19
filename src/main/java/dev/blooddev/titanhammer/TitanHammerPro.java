package dev.blooddev.titanhammer;

import dev.blooddev.titanhammer.commands.TitanHammerCommand;
import dev.blooddev.titanhammer.economy.EconomyManager;
import dev.blooddev.titanhammer.handlers.*;
import dev.blooddev.titanhammer.listeners.*;
import dev.blooddev.titanhammer.managers.*;
import dev.blooddev.titanhammer.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TitanHammerPro extends JavaPlugin {

    private static TitanHammerPro instance;
    
    // Managers
    private ConfigManager configManager;
    private DataManager dataManager;
    private PlayerDataManager playerDataManager;
    private SkillTreeManager skillTreeManager;
    private UpgradeManager upgradeManager;
    private BlockFilterManager blockFilterManager;
    private EconomyManager economyManager;
    private ParticleManager particleManager;
    private SoundManager soundManager;
    
    // Handlers
    private MythicMobsHandler mythicMobsHandler;

    @Override
    public void onEnable() {
        instance = this;
        
        long startTime = System.currentTimeMillis();
        
        getLogger().info("§e╔════════════════════════════════╗");
        getLogger().info("§e║   TitanHammer Pro Loading...  ║");
        getLogger().info("§e╚════════════════════════════════╝");
        
        // Initialize Managers
        initializeManagers();
        
        // Register Listeners
        registerListeners();
        
        // Register Commands
        registerCommands();
        
        // Check Dependencies
        checkDependencies();
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        getLogger().info("§a╔════════════════════════════════╗");
        getLogger().info("§a║  TitanHammer Pro Enabled!     ║");
        getLogger().info("§a║  Version: 1.0.0                ║");
        getLogger().info("§a║  Author: Blooddev              ║");
        getLogger().info("§a║  Load Time: " + loadTime + "ms" + "           ║");
        getLogger().info("§a╚════════════════════════════════╝");
    }

    @Override
    public void onDisable() {
        getLogger().info("§c╔════════════════════════════════╗");
        getLogger().info("§c║   TitanHammer Pro Disabling... ║");
        getLogger().info("§c╚════════════════════════════════╝");
        
        // Save all player data
        if (playerDataManager != null) {
            playerDataManager.saveAllPlayerData();
        }
        
        // Close database connections
        if (dataManager != null) {
            dataManager.closeConnection();
        }
        
        getLogger().info("§c╔════════════════════════════════╗");
        getLogger().info("§c║  TitanHammer Pro Disabled!     ║");
        getLogger().info("§c╚════════════════════════════════╝");
    }

    private void initializeManagers() {
        getLogger().info("§eInitializing managers...");
        
        configManager = new ConfigManager(this);
        configManager.loadConfigs();
        
        dataManager = new DataManager(this);
        dataManager.initialize();
        
        playerDataManager = new PlayerDataManager(this);
        skillTreeManager = new SkillTreeManager(this);
        upgradeManager = new UpgradeManager(this);
        blockFilterManager = new BlockFilterManager(this);
        economyManager = new EconomyManager(this);
        particleManager = new ParticleManager(this);
        soundManager = new SoundManager(this);
        
        getLogger().info("§aAll managers initialized successfully!");
    }

    private void registerListeners() {
        getLogger().info("§eRegistering event listeners...");
        
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        
        getLogger().info("§aAll listeners registered successfully!");
    }

    private void registerCommands() {
        getLogger().info("§eRegistering commands...");
        
        getCommand("titanhammer").setExecutor(new TitanHammerCommand(this));
        
        getLogger().info("§aAll commands registered successfully!");
    }

    private void checkDependencies() {
        getLogger().info("§eChecking dependencies...");
        
        // Check Vault
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            getLogger().info("§a✓ Vault found - Economy support enabled!");
            economyManager.setupVault();
        } else {
            getLogger().warning("§e⚠ Vault not found - Vault economy disabled");
        }
        
        // Check PlayerPoints
        if (Bukkit.getPluginManager().getPlugin("PlayerPoints") != null) {
            getLogger().info("§a✓ PlayerPoints found - PlayerPoints support enabled!");
            economyManager.setupPlayerPoints();
        } else {
            getLogger().warning("§e⚠ PlayerPoints not found - PlayerPoints economy disabled");
        }
        
        // Check MythicMobs
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            getLogger().info("§a✓ MythicMobs found - MythicMobs support enabled!");
            mythicMobsHandler = new MythicMobsHandler(this);
        } else {
            getLogger().warning("§e⚠ MythicMobs not found - MythicMobs features disabled");
        }
    }

    // Getters
    public static TitanHammerPro getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public SkillTreeManager getSkillTreeManager() {
        return skillTreeManager;
    }

    public UpgradeManager getUpgradeManager() {
        return upgradeManager;
    }

    public BlockFilterManager getBlockFilterManager() {
        return blockFilterManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public ParticleManager getParticleManager() {
        return particleManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MythicMobsHandler getMythicMobsHandler() {
        return mythicMobsHandler;
    }
}
