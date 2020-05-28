package it.marcodemartino.teams.listeners;

import it.marcodemartino.teams.utils.Match;
import it.marcodemartino.teams.utils.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

public class JoinListener implements Listener {

    private final Match match;

    public JoinListener() {
        match = new Match(1, 4);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SURVIVAL);

        match.addPlayerToRandomTeam(player.getUniqueId());
        player.teleport(match.getSpawnLocation());
        player.sendMessage("Ti ho aggiunto nel team " + match.getTeamOfPlayer(player.getUniqueId()).getTeamName());

        if (match.canMatchStart() && !match.isStarting()) {
            match.startCountDown();
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (match.isEnded())
            return;

        Player player = event.getPlayer();
        Team team = match.getTeamOfPlayer(player.getUniqueId());

        if (team.isPlayerDead(player.getUniqueId())) return;
        match.registerDeath(player.getUniqueId());

        Bukkit.broadcastMessage(player.getName() + " è uscito durante la partita");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (match.isEnded())
            return;

        Player player = event.getEntity();
        Team team = match.getTeamOfPlayer(player.getUniqueId());

        if (team.isPlayerDead(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        match.registerDeath(player.getUniqueId());
        event.setDeathMessage(String.format("§c%s §eè stato ucciso da §a%s", player.getName(), player.getKiller().getName()));

        List<Team> teamsAlive = match.getAliveTeam();

        if (teamsAlive.size() == 1) {
            Bukkit.broadcastMessage("Match finito, team vincitore: " + teamsAlive.get(0).getTeamName());
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

}
