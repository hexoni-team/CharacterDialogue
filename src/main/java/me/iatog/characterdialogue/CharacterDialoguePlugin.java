package me.iatog.characterdialogue;

import me.iatog.characterdialogue.api.CharacterDialogueAPI;
import me.iatog.characterdialogue.dialogs.DialogChoice;
import me.iatog.characterdialogue.dialogs.DialogMethod;
import me.iatog.characterdialogue.hook.Hooks;
import me.iatog.characterdialogue.interfaces.FileFactory;
import me.iatog.characterdialogue.libraries.ApiImplementation;
import me.iatog.characterdialogue.libraries.Cache;
import me.iatog.characterdialogue.loader.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.Map;

public class CharacterDialoguePlugin extends JavaPlugin {

    private PluginLoader loader;
    private FileFactory fileFactory;
    private Cache cache;
    private CharacterDialogueAPI api;
    private Hooks hooks;
    private String defaultChannel;
    private long startup;

    private static CharacterDialoguePlugin instance;

    public void onLoad() {
        this.defaultChannel = "BungeeCord";
        this.startup = System.currentTimeMillis();
    }

    @Override
    public void onEnable() {
        instance = this;
        Messenger messenger = getServer().getMessenger();
        if (!messenger.isOutgoingChannelRegistered(this, defaultChannel)) { // idk
            messenger.registerOutgoingPluginChannel(this, defaultChannel);
        }

        this.cache = new Cache();
        this.loader = new PluginLoader(this);
        this.hooks = new Hooks();
        this.api = new ApiImplementation(this);

        loader.load();
        getLogger().info("§aLoaded in " + (System.currentTimeMillis() - startup) + "ms");
    }

    @Override
    public void onDisable() {
        loader.unload();
    }

    public FileFactory getFileFactory() {
        return fileFactory;
    }

    public Cache getCache() {
        return cache;
    }

    public CharacterDialogueAPI getApi() {
        return api;
    }

    public void setDefaultFileFactory(FileFactory factory) {
        this.fileFactory = factory;
    }

    public Hooks getHooks() {
        return hooks;
    }

    /**
     * Register your own methods
     *
     * @param methods methods to register
     */

    @SuppressWarnings("unchecked")
    public void registerMethods(DialogMethod<? extends JavaPlugin>... methods) {
        Map<String, DialogMethod<? extends JavaPlugin>> mapMethods = getCache().getMethods();

        for (DialogMethod<? extends JavaPlugin> method : methods) {
            if (mapMethods.containsKey(method.getID())) {
                continue;
            }

            mapMethods.put(method.getID(), method);
            if (method instanceof Listener) {
                PluginManager pluginManager = Bukkit.getPluginManager();
                JavaPlugin provider = method.getProvider() == null ? this : method.getProvider();

                pluginManager.registerEvents((Listener) method, provider);
            }
        }
    }

    public void registerChoices(DialogChoice... choices) {
        Map<String, DialogChoice> choiceCache = getCache().getChoices();

        for (DialogChoice choice : choices) {
            if (choiceCache.containsKey(choice.getId())) {
                continue;
            }

            choiceCache.put(choice.getId(), choice);
        }

    }

    /**
     * I only set this method for third party plugins, I do not use this method and
     * even less abuse it. <br>
     * <br>
     * Get the main class of the CharacterDialogue.
     *
     * @return the plugin main class
     */
    public static CharacterDialoguePlugin getInstance() {
        return instance;
    }

}