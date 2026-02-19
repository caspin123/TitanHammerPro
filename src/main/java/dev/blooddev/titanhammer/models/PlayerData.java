package dev.blooddev.titanhammer.models;

import org.bukkit.Material;

import java.util.*;

public class PlayerData {
    
    private final UUID uuid;
    private String playerName;
    
    // Level System
    private int level;
    private double experience;
    private int skillPoints;
    
    // Skills
    private Map<String, Integer> skills;
    
    // Upgrades
    private int damageLevel;
    private int efficiencyLevel;
    private int fortuneLevel;
    private int durabilityLevel;
    private int rangeLevel;
    
    // Block Filter
    private FilterMode filterMode;
    private Set<Material> filteredBlocks;
    
    // Statistics
    private long blocksDestroyed;
    private long mobsKilled;
    private double totalDamage;
    
    // Settings
    private boolean particlesEnabled;
    private boolean soundsEnabled;
    private String language;
    
    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.level = 1;
        this.experience = 0.0;
        this.skillPoints = 0;
        this.skills = new HashMap<>();
        this.damageLevel = 0;
        this.efficiencyLevel = 0;
        this.fortuneLevel = 0;
        this.durabilityLevel = 0;
        this.rangeLevel = 0;
        this.filterMode = FilterMode.NONE;
        this.filteredBlocks = new HashSet<>();
        this.blocksDestroyed = 0;
        this.mobsKilled = 0;
        this.totalDamage = 0.0;
        this.particlesEnabled = true;
        this.soundsEnabled = true;
        this.language = "en";
    }
    
    // XP and Level System
    public void addExperience(double amount) {
        this.experience += amount;
        checkLevelUp();
    }
    
    private void checkLevelUp() {
        double requiredXP = getRequiredExperience();
        while (experience >= requiredXP) {
            experience -= requiredXP;
            level++;
            skillPoints++;
            requiredXP = getRequiredExperience();
        }
    }
    
    public double getRequiredExperience() {
        return 100 + (level * 50) + (level * level * 10);
    }
    
    public double getExperienceProgress() {
        return (experience / getRequiredExperience()) * 100.0;
    }
    
    // Skills
    public void setSkillLevel(String skillName, int level) {
        skills.put(skillName, level);
    }
    
    public int getSkillLevel(String skillName) {
        return skills.getOrDefault(skillName, 0);
    }
    
    public boolean hasSkill(String skillName) {
        return skills.containsKey(skillName) && skills.get(skillName) > 0;
    }
    
    public void addSkillPoints(int amount) {
        this.skillPoints += amount;
    }
    
    public boolean spendSkillPoints(int amount) {
        if (skillPoints >= amount) {
            skillPoints -= amount;
            return true;
        }
        return false;
    }
    
    // Upgrades
    public boolean canUpgrade(String upgradeType) {
        switch (upgradeType.toLowerCase()) {
            case "damage":
                return damageLevel < 10;
            case "efficiency":
                return efficiencyLevel < 10;
            case "fortune":
                return fortuneLevel < 5;
            case "durability":
                return durabilityLevel < 10;
            case "range":
                return rangeLevel < 5;
            default:
                return false;
        }
    }
    
    public void upgradeLevel(String upgradeType) {
        switch (upgradeType.toLowerCase()) {
            case "damage":
                damageLevel++;
                break;
            case "efficiency":
                efficiencyLevel++;
                break;
            case "fortune":
                fortuneLevel++;
                break;
            case "durability":
                durabilityLevel++;
                break;
            case "range":
                rangeLevel++;
                break;
        }
    }
    
    public double getDamageBonus() {
        return 1.0 + (damageLevel * 0.15); // +15% per level
    }
    
    public int getEfficiencyBonus() {
        return efficiencyLevel * 2; // +2 efficiency levels per upgrade
    }
    
    public int getFortuneBonus() {
        return fortuneLevel; // +1 fortune per level
    }
    
    public double getDurabilityMultiplier() {
        return 1.0 + (durabilityLevel * 0.1); // +10% durability per level
    }
    
    public int getBreakRange() {
        return 1 + rangeLevel; // 1 = 3x3, 2 = 5x5, etc
    }
    
    // Block Filter
    public void setFilterMode(FilterMode mode) {
        this.filterMode = mode;
    }
    
    public void addFilteredBlock(Material material) {
        filteredBlocks.add(material);
    }
    
    public void removeFilteredBlock(Material material) {
        filteredBlocks.remove(material);
    }
    
    public void clearFilteredBlocks() {
        filteredBlocks.clear();
    }
    
    public boolean isBlockFiltered(Material material) {
        return filteredBlocks.contains(material);
    }
    
    public boolean shouldAutoCollect(Material material) {
        return filterMode == FilterMode.AUTO_COLLECT && isBlockFiltered(material);
    }
    
    public boolean shouldAutoDelete(Material material) {
        return filterMode == FilterMode.AUTO_DELETE && isBlockFiltered(material);
    }
    
    // Statistics
    public void addBlocksDestroyed(long amount) {
        this.blocksDestroyed += amount;
    }
    
    public void addMobKilled() {
        this.mobsKilled++;
    }
    
    public void addDamageDealt(double amount) {
        this.totalDamage += amount;
    }
    
    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getEfficiencyLevel() {
        return efficiencyLevel;
    }

    public void setEfficiencyLevel(int efficiencyLevel) {
        this.efficiencyLevel = efficiencyLevel;
    }

    public int getFortuneLevel() {
        return fortuneLevel;
    }

    public void setFortuneLevel(int fortuneLevel) {
        this.fortuneLevel = fortuneLevel;
    }

    public int getDurabilityLevel() {
        return durabilityLevel;
    }

    public void setDurabilityLevel(int durabilityLevel) {
        this.durabilityLevel = durabilityLevel;
    }

    public int getRangeLevel() {
        return rangeLevel;
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }

    public FilterMode getFilterMode() {
        return filterMode;
    }

    public Set<Material> getFilteredBlocks() {
        return filteredBlocks;
    }

    public void setFilteredBlocks(Set<Material> filteredBlocks) {
        this.filteredBlocks = filteredBlocks;
    }

    public long getBlocksDestroyed() {
        return blocksDestroyed;
    }

    public void setBlocksDestroyed(long blocksDestroyed) {
        this.blocksDestroyed = blocksDestroyed;
    }

    public long getMobsKilled() {
        return mobsKilled;
    }

    public void setMobsKilled(long mobsKilled) {
        this.mobsKilled = mobsKilled;
    }

    public double getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(double totalDamage) {
        this.totalDamage = totalDamage;
    }

    public boolean isParticlesEnabled() {
        return particlesEnabled;
    }

    public void setParticlesEnabled(boolean particlesEnabled) {
        this.particlesEnabled = particlesEnabled;
    }

    public boolean isSoundsEnabled() {
        return soundsEnabled;
    }

    public void setSoundsEnabled(boolean soundsEnabled) {
        this.soundsEnabled = soundsEnabled;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public enum FilterMode {
        NONE,
        AUTO_COLLECT,
        AUTO_DELETE
    }
}
