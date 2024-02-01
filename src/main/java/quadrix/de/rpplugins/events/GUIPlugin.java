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
import org.bukkit.plugin.java.JavaPlugin;

public class GUIPlugin extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        getCommand("testgui").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        Player player = (Player) sender;
        openMainGUI(player);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() != Material.AIR) {
            if (clickedItem.getType() == Material.DIAMOND_SWORD) {
                openGUI(player, 1);
            } else if (clickedItem.getType() == Material.GOLDEN_SWORD) {
                openGUI(player, 2);
            } else if (clickedItem.getType() == Material.IRON_SWORD) {
                openGUI(player, 3);
            } else if (clickedItem.getType() == Material.STONE_SWORD) {
                openGUI(player, 4);
            } else if (clickedItem.getType() == Material.WOODEN_SWORD) {
                openGUI(player, 5);
            }
        }
    }

    private void openMainGUI(Player player) {
        Inventory mainGUI = Bukkit.createInventory(null, 9, "Haupt-GUI");

        ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack item2 = new ItemStack(Material.GOLDEN_SWORD);
        ItemStack item3 = new ItemStack(Material.IRON_SWORD);
        ItemStack item4 = new ItemStack(Material.STONE_SWORD);
        ItemStack item5 = new ItemStack(Material.WOODEN_SWORD);

        mainGUI.setItem(2, item1);
        mainGUI.setItem(3, item2);
        mainGUI.setItem(4, item3);
        mainGUI.setItem(5, item4);
        mainGUI.setItem(6, item5);

        player.openInventory(mainGUI);
    }

    private void openGUI(Player player, int guiNumber) {
        Inventory gui = Bukkit.createInventory(null, 9, "GUI " + guiNumber);

        // Hier kannst du die Inhalte der einzelnen GUIs anpassen
        ItemStack itemInGUI = new ItemStack(Material.STONE);

        gui.setItem(4, itemInGUI);

        player.openInventory(gui);
    }
}
