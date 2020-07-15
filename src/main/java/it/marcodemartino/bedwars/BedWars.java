package it.marcodemartino.bedwars;

import it.marcodemartino.bedwars.commands.BedwarsCommand;
import it.marcodemartino.bedwars.listeners.Listeners;
import it.marcodemartino.bedwars.utils.ArenaManager;
import it.marcodemartino.bedwars.utils.FileManager;
import it.marcodemartino.supermarcomenlib.commands.Commands;
import org.bukkit.plugin.java.JavaPlugin;

public class BedWars extends JavaPlugin {

    @Override
    public void onEnable() {
        FileManager fileManager = new FileManager(this);

        Listeners listeners = new Listeners();
        getServer().getPluginManager().registerEvents(listeners, this);

        ArenaManager arenaManager = new ArenaManager(fileManager, listeners);
        arenaManager.loadArenas(getDataFolder());
        Commands.registerCommand(this, "bedwars", new BedwarsCommand(arenaManager));
    }
}
