package quadrix.de.rpplugins.commands;






import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import quadrix.de.rpplugins.util.ItemBuilder;
import quadrix.de.rpplugins.warp.WarpManager;
import quadrix.de.rpplugins.warp.Warps;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TeleporterCommand implements CommandExecutor, Listener {



    private final HashMap<Player, String> playerWarpSelections = new HashMap<>();

    private final File configFile = new File("plugins/Warps/warps.yml");
    private final JavaPlugin plugin;

    public TeleporterCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        loadWarpSelections();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        Player player = (Player) sender;
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();

        integerItemStackHashMap.put(10, new ItemBuilder(Material.OAK_BOAT).setDisplayname("Hafen").setLocalizedName("hafen").build());
        integerItemStackHashMap.put(16, new ItemBuilder(Material.BARREL).setDisplayname("Markt").setLocalizedName("markt").build());
        integerItemStackHashMap.put(22, new ItemBuilder(Material.GRASS_BLOCK).setDisplayname("Stadt").setLocalizedName("stadt").build());
        integerItemStackHashMap.put(37, new ItemBuilder(Material.COAL_ORE).setDisplayname("Mine").setLocalizedName("mine").build());
        integerItemStackHashMap.put(43, new ItemBuilder(Material.OAK_DOOR).setDisplayname("Homes").setLocalizedName("homes").build());

        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "§dWarp§9Crystal");

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            inventory.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }

        player.openInventory(inventory);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || !event.getView().getTitle().equals("§dWarp§9Crystal")) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        String warpName = Objects.requireNonNull(clickedItem.getItemMeta()).getLocalizedName();
        playerWarpSelections.put(player, warpName); // Speichere die Warp-Auswahl des Spielers

        // Überprüfen, ob sich 2 Blöcke vor dem Spieler Luft befinden
        Location playerLocation = player.getLocation();
        Location twoBlocksAhead = playerLocation.add(playerLocation.getDirection().multiply(2));
        Block blockTwoAhead = twoBlocksAhead.getBlock();

        if (blockTwoAhead.getType() == Material.AIR) {
            // Wenn leerer Raum vorhanden ist, platziere ein Netherportal
            blockTwoAhead.setType(Material.NETHER_PORTAL);

            player.sendMessage("Gehe durch das Netherportal, um dich zum Warp-Punkt zu teleportieren.");

            // Zerstöre das Portal nach 10 Sekunden
            destroyPortalAfterDelay(blockTwoAhead.getLocation());
        } else {
            player.sendMessage("Du benötigst mehr Platz, um das Portal zu platzieren.");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (playerWarpSelections.containsKey(player)) {
            // Überprüfen, ob der Spieler eine Warp-Auswahl getroffen hat und sich in einem anderen Block befindet
            if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                String warpName = playerWarpSelections.get(player);
                playerWarpSelections.remove(player);

                Location targetLocation = WarpManager.getWarp(Warps.valueOf(warpName));

                if (targetLocation != null) {
                    player.teleport(targetLocation);
                }
            }
        }
    }

    // Laden der Warp-Auswahlen aus der Konfigurationsdatei
    private void loadWarpSelections() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for (String key : config.getKeys(false)) {
            String warpName = config.getString(key);
            playerWarpSelections.put(Bukkit.getPlayer(UUID.fromString(key)), warpName);
        }
    }

    // Speichern der Warp-Auswahlen in der Konfigurationsdatei
    private void saveWarpSelections() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for (Map.Entry<Player, String> entry : playerWarpSelections.entrySet()) {
            config.set(entry.getKey().getUniqueId().toString(), entry.getValue());
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Portal nach einer Verzögerung von 10 Sekunden zerstören
    private void destroyPortalAfterDelay(Location location) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Block portalBlock = location.getBlock();
                if (portalBlock.getType() == Material.NETHER_PORTAL) {
                    portalBlock.setType(Material.AIR);
                }
            }
        }.runTaskLater(plugin, 20 * 10); // 10 Sekunden Verzögerung (20 Ticks pro Sekunde)
    }
}
