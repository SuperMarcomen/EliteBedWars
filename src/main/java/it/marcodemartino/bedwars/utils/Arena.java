package it.marcodemartino.bedwars.utils;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Arena {

    private Location waitLobby;
    private int minPlayer = 2;
    private Map<String, ArenaTeam> teams = new HashMap<>();

    public void sendGlobalMessage(String message) {
        teams.values().forEach(arenaTeam -> arenaTeam.sendGlobalMessage(message));
    }

    public boolean isMatchEnded() {
        int teamAlive = 0;
        for (ArenaTeam arenaTeam : teams.values()) {
            if (arenaTeam.isBedDestroyed() && arenaTeam.areAllPlayersDead()) {
                continue;
            }
            teamAlive++;
        }
        return teamAlive <= 1;
    }

    public ArenaTeam getTeamFromBed(Location location) {
        for (ArenaTeam arenaTeam : teams.values()) {
            if (arenaTeam.getBedLocation().equals(location))
                return arenaTeam;
        }
        return null;
    }

    public ArenaTeam getTeamOf(Player player) {
        for (ArenaTeam arenaTeam : teams.values()) {
            if(arenaTeam.containsPlayer(player))
                return arenaTeam;
        }

        return null;
    }

    public Arena isPlayerInArena(Player player) {
        for (ArenaTeam arenaTeam : teams.values()) {
            if(arenaTeam.containsPlayer(player))
                return this;
        }

        return null;
    }

    public ArenaTeam createTeam(String name) {
        ArenaTeam arenaTeam = new ArenaTeam();
        arenaTeam.setName(name);
        teams.put(name, arenaTeam);
        return arenaTeam;
    }

    public boolean canStart() {
        int totalPlayers = 0;
        for (ArenaTeam arenaTeam : teams.values()) {
            int playersNumber = arenaTeam.getPlayersNumber();
            totalPlayers += playersNumber;
        }

        return totalPlayers >= minPlayer;
    }

    public Location getWaitLobby() {
        return waitLobby;
    }

    public void setWaitLobby(Location waitLobby) {
        this.waitLobby = waitLobby;
    }

    public void addPlayerToEmptiestTeam(Player player) {
        ArenaTeam bestArenaTeam = teams.values().iterator().next();
        Comparator<ArenaTeam> comparator = Comparator.comparingInt(ArenaTeam::getPlayersNumber);
        for (ArenaTeam arenaTeam : teams.values()) {
            if (comparator.compare(arenaTeam, bestArenaTeam) < 0) {
                bestArenaTeam = arenaTeam;
            }
        }
        bestArenaTeam.addPlayer(player);
    }

    protected Map<String, ArenaTeam> getTeams() {
        return teams;
    }

    public ArenaTeam getTeam(String name) {
        return teams.get(name);
    }

    public static Arena fromConfig(FileManager.Config config) {
        Arena arena = new Arena();
        ConfigurationSection configurationSection = (ConfigurationSection) config.getValue("teams");
        for (String key : configurationSection.getKeys(false)) {
            System.out.println(configurationSection.getString(key));
            arena.createTeam(key).setBedLocation(LocationSerialize.deserialize(configurationSection.getString(key)));
        }
        return arena;
    }
}
