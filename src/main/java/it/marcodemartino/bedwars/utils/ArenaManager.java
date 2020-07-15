package it.marcodemartino.bedwars.utils;

import it.marcodemartino.bedwars.listeners.Listeners;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private FileManager fileManager;
    private Map<String, Arena> arenas = new HashMap<>();
    private Listeners listeners;

    public ArenaManager(FileManager fileManager, Listeners listeners) {
        this.fileManager = fileManager;
        this.listeners = listeners;
    }

    public void startArena(Arena arena) {
        arena.getTeams().values().forEach(ArenaTeam::teleportAllPlayers);
        Bukkit.broadcastMessage("Partita iniziata!");
        listeners.addActiveArena(arena);
    }

    public void loadArenas(File dataFolder) {
        File[] directoryListing = new File(dataFolder, "arenas").listFiles(); //TODO check
        if (directoryListing == null) return;
        for (File configFile : directoryListing) {
            String fileName = configFile.getName();
            FileManager.Config config = fileManager.getConfig(fileName);
            config.reload();
            Arena arena = Arena.fromConfig(config);
            arenas.put(fileName.replace(".yml", ""), arena);
        }
    }

    public void saveArena(String name) {
        Arena arena = arenas.get(name);
        if (arena == null) return; //TODO do something

        FileManager.Config config = fileManager.getConfig("arenas/" + name + ".yml");
        arena.getTeams().forEach((teamName, team) -> config.setValue("teams." + teamName, LocationSerialize.serialize(team.getBedLocation())));
        config.setValue("wait_lobby", LocationSerialize.serialize(arena.getWaitLobby()));
        config.createConfig();
    }

    public void addArena(String name) {
        arenas.put(name, new Arena());
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }
}
