package it.marcodemartino.bedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerialize {

    /**
     * Serialize the Location to a String
     *
     * @param loc The Location
     * @return a String
     */
    public static String serialize(Location loc) {
        if(loc == null)
            return null;

        return loc.getWorld().getName() + ":" +
                loc.getX() + ":" +
                loc.getY() + ":" +
                loc.getZ() + ":" +
                loc.getYaw() + ":" +
                loc.getPitch();
    }

    /**
     * Deserialize the String to a Location
     *
     * @param s The String
     * @return the Location (null if the string isn't valid)
     */
    public static Location deserialize(String s) {
        if(s == null || s.isEmpty())
            return null;

        String[] splitted = s.split(":");

        if(splitted.length < 6)
            return null;

        return new Location(
                Bukkit.getWorld(splitted[0]),
                Double.parseDouble(splitted[1]),
                Double.parseDouble(splitted[2]),
                Double.parseDouble(splitted[3]),
                Float.parseFloat(splitted[4]),
                Float.parseFloat(splitted[5])
        );
    }

}

