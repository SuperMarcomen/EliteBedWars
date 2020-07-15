package it.marcodemartino.bedwars.listeners;

import it.marcodemartino.bedwars.utils.Arena;
import it.marcodemartino.bedwars.utils.ArenaTeam;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public class Listeners implements Listener {

    private List<Arena> activeArenas = new ArrayList<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.BED_BLOCK) return;

        Player player = event.getPlayer();
        Arena arena = getPlayerArena(player);
        if (arena == null) return;

        ArenaTeam arenaTeam = arena.getTeamFromBed(block.getLocation());
        if (arenaTeam == null) throw new AssertionError("Un giocatore ha distrutto un letto che non è collegato a nessun team (Forse c'è qualche errore nella configurazione)!");

        arenaTeam.setBedDestroyed(true);
        arena.sendGlobalMessage("Il letto dei " + arenaTeam.getName() + " è stato distrutto");

        if (arena.isMatchEnded()) {
            arena.sendGlobalMessage("Partita finita!");
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Arena arena = getPlayerArena(player);
        if (arena == null) return;

        ArenaTeam arenaTeam = arena.getTeamOf(player);
        if (arenaTeam == null) throw new AssertionError("Il team del giocatore " + player.getName() + " non è stato trovato!");

        if (arenaTeam.isBedDestroyed()) {
            player.setGameMode(GameMode.SPECTATOR);
            arena.sendGlobalMessage(player.getName() + " è morto per SEMPRE");
        } else {
            arena.sendGlobalMessage(player.getName() + " è morto");
        }
    }

    private Arena getPlayerArena(Player player) {
        for (Arena arena : activeArenas) {
            if(arena.isPlayerInArena(player) != null)
                return arena;
        }

        return null;
    }

    public void addActiveArena(Arena arena) {
        activeArenas.add(arena);
    }
}
