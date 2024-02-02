package theriseofavalon.de.warps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import theriseofavalon.de.warps.warp.WarpManager;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        Player p = (Player) sender;

        // Überprüfe, ob der Spieler die Berechtigung "warp.manage" hat.
        if (!p.hasPermission("warp.manage")) {
            p.sendMessage("§cKeine Rechte!");
            return true;
        }

        if (args.length == 1) {
            if (WarpManager.getWarp(args[0]) != null) {
                p.teleport(WarpManager.getWarp(args[0]));
                p.sendMessage("§aDu wurdest zu dem Warp " + args[0] + " teleportiert");
            } else {
                p.sendMessage("§cDiesen Warp gibt es nicht");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (WarpManager.getWarp(args[1]) == null) {
                    WarpManager.createWarp(args[1], p.getLocation());
                    p.sendMessage("§aDu hast den Warp " + args[1] + " erstellt");
                } else {
                    p.sendMessage("§cDiesen Warp gibt es schon");
                }
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (WarpManager.getWarp(args[1]) != null) {
                    WarpManager.deleteWarp(args[1]);
                    p.sendMessage("§aDu hast den Warp " + args[1] + " gelöscht");
                } else {
                    p.sendMessage("§cDiesen Warp gibt es nicht");
                }
            }
        }
        return true;
    }
}



