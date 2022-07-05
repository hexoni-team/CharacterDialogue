package me.iatog.characterdialogue.loader;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.filter.ConsoleFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.List;

public class PluginLoader implements Loader {

    private final CharacterDialoguePlugin main;
    private final List<Loader> loaders;

    public PluginLoader(CharacterDialoguePlugin main) {
        this.main = main;
        this.loaders = new ArrayList<>();
    }

    @Override
    public void load() {
        ((Logger) LogManager.getRootLogger()).addFilter(new ConsoleFilter());

        loadLoaders(
                new ListenerLoader(main),
                new FileLoader(main),
                new CacheLoader(main),
                new CommandLoader(main),
                new DialogLoader(main)
        );

        main.getLogger().info("§a" + main.getDescription().getName() + " enabled. §7" + main.getDescription().getVersion());
    }

    @Override
    public void unload() {
        loaders.forEach(Loader::unload);
        loaders.clear();
    }

    public List<Loader> getLoaders() {
        return loaders;
    }

    private void loadLoaders(Loader... loaders) {
        for (Loader loader : loaders) {
            loader.load();
            this.loaders.add(loader);
        }
    }

}
