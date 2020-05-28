package it.marcodemartino.teams.utils;

import java.util.*;

public class Team {

    private String teamName;
    private HashSet<UUID> playersUUID;
    private HashSet<UUID> deadPlayers;

    public Team(String teamName) {
        this.teamName = teamName;
        playersUUID = new HashSet<>();
        deadPlayers = new HashSet<>();
    }

    public void addPlayerToTeam(UUID uuid) {
        playersUUID.add(uuid);
    }

    public boolean isPlayerDead(UUID uuid) {
        return deadPlayers.contains(uuid);
    }

    public void addPlayerDeath(UUID uuid) {
        deadPlayers.add(uuid);
    }

    public boolean isTeamDead() {
        for (UUID uuid : playersUUID) {
            if (!deadPlayers.contains(uuid))
                return false;
        }

        return true;
    }

    public String getTeamName() {
        return teamName;
    }

    public HashSet<UUID> getPlayersUUID() {
        return playersUUID;
    }
}
