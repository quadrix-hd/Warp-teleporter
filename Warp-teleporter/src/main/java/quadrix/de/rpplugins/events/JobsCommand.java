package quadrix.de.rpplugins.events;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import quadrix.de.rpplugins.util.ItemBuilder;

import java.util.HashMap;
import java.util.Map;

public class JobsCommand implements CommandExecutor, Listener {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        Player player = (Player) sender;
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();

        integerItemStackHashMap.put(10, new ItemBuilder(Material.OAK_BOAT).setDisplayname("Fischer").setLocalizedName("hafen").build());
        integerItemStackHashMap.put(16, new ItemBuilder(Material.BARREL).setDisplayname("Markt").setLocalizedName("markt").build());
        integerItemStackHashMap.put(22, new ItemBuilder(Material.GRASS_BLOCK).setDisplayname("Stadt").setLocalizedName("stadt").build());
        integerItemStackHashMap.put(37, new ItemBuilder(Material.COAL_ORE).setDisplayname("Mine").setLocalizedName("mine").build());
        integerItemStackHashMap.put(43, new ItemBuilder(Material.OAK_DOOR).setDisplayname("Homes").setLocalizedName("homes").build());

        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "§2Jobs");

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            inventory.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }

        player.openInventory(inventory);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || !event.getView().getTitle().equals("§2Jobs")) {
            return;
        }
    }
}

