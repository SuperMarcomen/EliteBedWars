package it.marcodemartino.bedwars.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaTeam {

    private String name;
    private Location bedLocation;
    private boolean bedDestroyed = false;
    private List<Player> players = new ArrayList<>();

    protected void sendGlobalMessage(String message) {
        players.forEach(player -> player.sendMessage(message));
    }

    protected boolean areAllPlayersDead() {
        for (Player player : players) {
            if (player.getGameMode() != GameMode.SPECTATOR)
                return false;
        }
        return true;
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public void teleportAllPlayers() {
        players.forEach(player -> player.teleport(bedLocation));
    }

    public boolean isBedDestroyed() {
        return bedDestroyed;
    }

    public void setBedDestroyed(boolean bedDestroyed) {
        this.bedDestroyed = bedDestroyed;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public Location getBedLocation() {
        return bedLocation;
    }

    public void setBedLocation(Location bedLocation) {
        this.bedLocation = bedLocation;
    }
}
