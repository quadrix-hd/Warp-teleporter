package theriseofavalon.de.warps;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import theriseofavalon.de.warps.commands.WarpCommand;
import theriseofavalon.de.warps.events.JobsCommand;
import theriseofavalon.de.warps.events.TeleporterCommand;
import theriseofavalon.de.warps.util.Config;

import java.util.UUID;

public final class Warps extends JavaPlugin implements Listener {
    private static Config cfg;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null){
            LuckPerms api = provider.getProvider();
        }
        //jobs
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("jobs").setExecutor(new JobsCommand());
        //warps
        Warps instance = this;
        getCommand("warp").setExecutor(new WarpCommand());
        cfg = new Config("warps.yml" , getDataFolder());
        //teleporter
        Bukkit.getPluginManager().registerEvents(new TeleporterCommand(this) , this);
        getCommand("teleporter").setExecutor(new TeleporterCommand(this));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Hier weisen wir dem Spieler die Variable "jobs" mit einem Standardwert zu (z.B. null)
        UUID playerUUID = event.getPlayer().getUniqueId();
        // Du kannst hier die Variable "jobs" mit einem bestimmten Wert initialisieren
        // Zum Beispiel: String jobs = "Arbeiter";
        // Dann kannst du die Variable dem Spieler zuweisen: event.getPlayer().setMetadata("jobs", new FixedMetadataValue(this, jobs));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Config getCfg() {
        return cfg;
    }
}
