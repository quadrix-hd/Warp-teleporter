package quadrix.de.rpplugins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quadrix.de.rpplugins.warp.WarpManager;
import quadrix.de.rpplugins.warp.Warps;

import java.util.Objects;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        // Überprüfe, ob der Spieler die Berechtigung "warp.manage" hat.
        if (!p.hasPermission("warp.manage")) {
            p.sendMessage("§cKeine Rechte!");
            return true;
        }

        if (args.length == 1) {
            try {
                Warps warpType = Warps.valueOf(args[0]);
                if (WarpManager.getWarp(warpType) != null) {
                    p.teleport(Objects.requireNonNull(WarpManager.getWarp(warpType)));
                    p.sendMessage("§aDu wurdest zu dem Warp " + args[0] + " teleportiert");
                } else {
                    p.sendMessage("§cDiesen Warp gibt es nicht");
                }
            } catch (IllegalArgumentException e) {
                p.sendMessage("§cUngültiger Warp-Name");
            }
        }
        return false;
    }
}
