package quadrix.de.rpplugins.warp;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Warp {
    private final Location location;
    private final ItemStack itemStack;

    public Warp(Location location, ItemStack itemStack) {
        this.location = location;
        this.itemStack = itemStack;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}