package it.marcodemartino.bedwars.subcommands;

import it.marcodemartino.bedwars.utils.Arena;
import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.supermarcomenlib.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class JoinArenaSubCommand implements Command {

    private ArenaManager arenaManager;

    public JoinArenaSubCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void executeCommand(CommandSender sender, String name, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("No");
            return;
        }
        Player player = (Player) sender;

        Arena arena = arenaManager.getArena(args[0]);
        if (arena == null) {
            player.sendMessage("Arena non trovata");
            return;
        }

        arena.addPlayerToEmptiestTeam(player);
        if (arena.canStart()) {
            arenaManager.startArena(arena);
        }
        player.sendMessage("Fatto");
        player.teleport(arena.getWaitLobby());
    }
}
