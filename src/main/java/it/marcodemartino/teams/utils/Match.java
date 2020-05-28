package it.marcodemartino.teams.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import static it.marcodemartino.teams.Teams.getPlugin;

public class Match {

    private List<Team> teams;
    private Location spawnLocation;
    private int minTeamSize;
    private int maxTeamSize;
    private boolean starting;

    public Match(int minTeamSize, int maxTeamSize) {
        this.teams = Arrays.asList(
                new Team("red"),
                new Team("blue")
        );
        this.minTeamSize = minTeamSize;
        this.maxTeamSize = maxTeamSize;
        spawnLocation = new Location(
                Bukkit.getWorld("world"),
                0,
                70,
                0
        );
    }

    public boolean isEnded() {
        return getAliveTeam().size() == 1;
    }

    public List<Team> getAliveTeam() {
        List<Team> teamsAlive = new ArrayList<>();
        for (Team team : teams) {
            if (!team.isTeamDead())
                teamsAlive.add(team);
        }

        return teamsAlive;
    }

    public void registerDeath(UUID uuid) {
        Team team = getTeamOfPlayer(uuid);
        team.addPlayerDeath(uuid);
    }

    public boolean canMatchStart() {
        int teamsWithMinimumPlayers = 0;
        for (Team team : teams) {
            if (team.getPlayersUUID().size() >= minTeamSize)
                teamsWithMinimumPlayers++;
        }

        return teamsWithMinimumPlayers >= 2;
    }

    public boolean isStarting() {
        return starting;
    }

    public void startCountDown() {
        starting = true;
        final int[] secondsLeft = {30};

        Bukkit.getScheduler().runTaskTimer(getPlugin(), () -> {
            if (areTeamFull()) {
                teleportPlayers();
                return;
            }

            Bukkit.broadcastMessage("Match starting in " + secondsLeft[0] + " seconds");

            if (secondsLeft[0] == 1) {
                teleportPlayers();
                return;
            }

            secondsLeft[0] = secondsLeft[0] -1;
        }, 20* secondsLeft[0], 20);
    }

    private void teleportPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(spawnLocation);
        }

        Bukkit.broadcastMessage("Game started");
    }

    public void addPlayerToRandomTeam(UUID uuid) {
        teams.sort(Comparator.comparing(team -> team.getPlayersUUID().size()));
        teams.get(0).addPlayerToTeam(uuid);
    }

    public Team getTeamOfPlayer(UUID uuid) {
        for (Team team : teams) {
            if (team.getPlayersUUID().contains(uuid))
                return team;
        }

        return null;
    }

    public boolean areTeamFull() {
        for (Team team : teams) {
            if (team.getPlayersUUID().size() < maxTeamSize) return false;
        }

        return true;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
