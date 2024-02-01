package quadrix.de.rpplugins.warp;


import org.bukkit.Location;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

public class WarpManager {
    private static final HashMap<String,Warp> warps = new HashMap<>();

    public void registerWarps() {
        Location location = new Location(Bukkit.getWorld("farmwelt"),1,1,1);
        ItemStack item = new ItemStack(Material.GRASS_BLOCK);

        Warp warp = new Warp(location,item);
        warps.put("farm",warp);


        World world = Bukkit.getWorld("nether");
        if(world == null)
            return;

        location = world.getSpawnLocation();
        item = new ItemStack(Material.NETHERRACK);

        warp = new Warp(location,item);
        warps.put("nether",warp);
    }

    public static @Nullable Location getWarp(Warps warp) {
        String name = warp.getName();
        if(!warps.containsKey(name)) return null;
        return warps.get(name).getLocation();
    }
}
