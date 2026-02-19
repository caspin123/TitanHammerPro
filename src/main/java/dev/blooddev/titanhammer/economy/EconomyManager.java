package dev.blooddev.titanhammer.economy;

import dev.blooddev.titanhammer.TitanHammerPro;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    private final TitanHammerPro plugin;
    private Economy economy;

    public EconomyManager(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    public void setupVault() {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null) {
                economy = rsp.getProvider();
            }
        }
    }

    public void setupPlayerPoints() {
        // PlayerPoints setup
    }

    public Economy getEconomy() {
        return economy;
    }
}
