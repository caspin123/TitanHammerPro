package dev.blooddev.titanhammer.commands;

import dev.blooddev.titanhammer.TitanHammerPro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitanHammerCommand implements CommandExecutor {
    private final TitanHammerPro plugin;

    public TitanHammerCommand(TitanHammerPro plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§6TitanHammer Pro v1.0.0");
            player.sendMessage("§e/th give <player> - Give hammer");
            player.sendMessage("§e/th reload - Reload config");
            return true;
        }

        return true;
    }
}
