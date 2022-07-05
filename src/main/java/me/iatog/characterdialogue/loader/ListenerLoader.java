package me.iatog.characterdialogue.loader;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.listeners.NPCInteractListener;
import me.iatog.characterdialogue.listeners.NPCSpawnListener;
import me.iatog.characterdialogue.listeners.PlayerJoinListener;
import me.iatog.characterdialogue.listeners.PlayerMoveListener;
import me.iatog.characterdialogue.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class ListenerLoader implements Loader {

    private final CharacterDialoguePlugin main;

    public ListenerLoader(CharacterDialoguePlugin main) {
        this.main = main;
    }

    @Override
    public void load() {
        registerListeners(
                new NPCInteractListener(main),
                new NPCSpawnListener(main),
                new PlayerQuitListener(main),
                new PlayerMoveListener(main),
                new PlayerJoinListener(main)
        );
    }

    public void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, main);
        }
    }
}
