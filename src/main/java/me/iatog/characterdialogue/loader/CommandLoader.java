package me.iatog.characterdialogue.loader;

import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.command.CharacterDialogueCommand;
import me.iatog.characterdialogue.command.DeveloperCommand;

public class CommandLoader implements Loader {

    private final CharacterDialoguePlugin main;
    private final AnnotatedCommandTreeBuilder builder;
    private final BukkitCommandManager commandManager;

    public CommandLoader(CharacterDialoguePlugin main) {
        this.main = main;
        this.commandManager = new BukkitCommandManager("CharacterDialogue");
        PartInjector injector = PartInjector.create();
        injector.install(new DefaultsModule());
        injector.install(new BukkitModule());
        this.builder = new AnnotatedCommandTreeBuilderImpl(injector);
    }

    @Override
    public void load() {
        registerCommands(
                new CharacterDialogueCommand(main),
                new DeveloperCommand()
        );
    }

    public void registerCommands(CommandClass... commands) {
        for (CommandClass command : commands) {
            commandManager.registerCommands(builder.fromClass(command));
        }
    }

}
