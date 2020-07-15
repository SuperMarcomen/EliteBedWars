package it.marcodemartino.bedwars.commands;

import com.google.common.collect.ImmutableMap;
import it.marcodemartino.bedwars.subcommands.*;
import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.supermarcomenlib.commands.Command;
import it.marcodemartino.supermarcomenlib.commands.TreeCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class BedwarsCommand extends TreeCommand {

    public BedwarsCommand(ArenaManager arenaManager) {
        super(ImmutableMap.<String, Command>builder()
                .put("createarena", new CreateArenaSubCommand(arenaManager))
                .put("createteam", new CreateTeamSubCommand(arenaManager))
                .put("setbed", new SetBedSubCommand(arenaManager))
                .put("setwaitlobby", new SetWaitLobbySubCommand(arenaManager))
                .put("savearena", new SaveArenaSubCommand(arenaManager))
                .put("joinarena", new JoinArenaSubCommand(arenaManager))
                .build()
        );
    }

    @Override
    public void executeDefaultCommand(CommandSender sender, String name, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("No");
            return;
        }

        sender.sendMessage("Magari fai altri comandi");
    }

    @Override
    public void executeUnknownCommand(CommandSender sender, String name, String[] args) {
        sender.sendMessage("Magari fai altri comandi pt. 2");
    }
}
