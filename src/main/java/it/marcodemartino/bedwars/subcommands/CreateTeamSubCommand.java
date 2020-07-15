package it.marcodemartino.bedwars.subcommands;

import it.marcodemartino.bedwars.utils.Arena;
import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.supermarcomenlib.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateTeamSubCommand implements Command {

    private ArenaManager arenaManager;

    public CreateTeamSubCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void executeCommand(CommandSender sender, String s, String[] args) {
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
        if (args.length != 2) {
            player.sendMessage("Uso corretto: /bedwars createteam [arena] [team]");
            return;
        }

        arena.createTeam(args[1]);
        player.sendMessage("Fatto");
    }
}
