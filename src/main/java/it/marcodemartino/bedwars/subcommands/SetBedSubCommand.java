package it.marcodemartino.bedwars.subcommands;

import it.marcodemartino.bedwars.utils.Arena;
import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.bedwars.utils.ArenaTeam;
import it.marcodemartino.supermarcomenlib.commands.Command;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetBedSubCommand implements Command {

    private ArenaManager arenaManager;

    public SetBedSubCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void executeCommand(CommandSender sender, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("No");
            return;
        }
        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage("Uso corretto: /bedwars setbed [arena] [team]");
            return;
        }

        Arena arena = arenaManager.getArena(args[0]);
        if (arena == null) {
            player.sendMessage("Arena non trovata");
            return;
        }
        ArenaTeam arenaTeam = arena.getTeam(args[1]);
        if (arenaTeam == null) {
            player.sendMessage("Team non trovato");
            return;
        }

        Block block = player.getTargetBlock(null, 5);
        if (!block.getType().equals(Material.BED_BLOCK)) {
            player.sendMessage("Non stai guardando un blocco");
            return;
        }

        arenaTeam.setBedLocation(block.getLocation());
        player.sendMessage("Fatto");
    }

}
