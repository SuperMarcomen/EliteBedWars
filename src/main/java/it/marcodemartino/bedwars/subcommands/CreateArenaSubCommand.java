package it.marcodemartino.bedwars.subcommands;

import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.supermarcomenlib.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateArenaSubCommand implements Command {

    private ArenaManager arenaManager;

    public CreateArenaSubCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void executeCommand(CommandSender sender, String name, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("No");
            return;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("Uso corretto: /bedwars createarena [nome]");
            return;
        }

        arenaManager.addArena(args[0]);
        player.sendMessage("Arena " + args[0] + " creata");

    }
}
