package me.iatog.characterdialogue.dialogs.method;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.dialogs.DialogMethod;
import me.iatog.characterdialogue.session.DialogSession;
import org.bukkit.entity.Player;

public class ExecuteIfMethod extends DialogMethod<CharacterDialoguePlugin> {

    public ExecuteIfMethod() {
        super("execute_if");
    }

    @SuppressWarnings("unused")
    @Override
    public void execute(Player player, String arg, DialogSession session) {
        String condition = arg.split(":")[0];
        String toExecute = arg.substring(condition.length());


    }
}
