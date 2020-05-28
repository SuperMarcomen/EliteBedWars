package it.marcodemartino.teams;

import it.marcodemartino.teams.listeners.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Teams extends JavaPlugin {

    private static Teams plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public static Teams getPlugin() {
        return plugin;
    }
}
