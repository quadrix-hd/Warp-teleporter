package theriseofavalon.de.warps.warp;

import org.bukkit.Location;
import theriseofavalon.de.warps.Warps;

public class WarpManager{

    public static Location getWarp(String name){
        return Warps.getCfg().getConfiguration().getLocation(name);}

    public static void createWarp(String name , Location location) {
        Warps.getCfg().set(name, location);
       Warps.getCfg().save();

    }
    public static void deleteWarp(String name) {
       Warps.getCfg().set(name, null);
        Warps.getCfg().save();

    }
}