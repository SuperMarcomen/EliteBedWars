package it.marcodemartino.bedwars.subcommands;

import it.marcodemartino.bedwars.utils.Arena;
import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.supermarcomenlib.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetWaitLobbySubCommand implements Command {

    private ArenaManager arenaManager;

    public SetWaitLobbySubCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void executeCommand(CommandSender sender, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("No");
            return;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Uso corretto: /bedwars setlobby [arena]");
            return;
        }

        Arena arena = arenaManager.getArena(args[0]);
        if (arena == null) {
            player.sendMessage("Arena non trovata");
            return;
        }

        arena.setWaitLobby(player.getLocation());
        player.sendMessage("Fatto");
    }
}
