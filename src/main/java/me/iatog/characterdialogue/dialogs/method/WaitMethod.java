package me.iatog.characterdialogue.dialogs.method;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.dialogs.DialogMethod;
import me.iatog.characterdialogue.session.DialogSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WaitMethod extends DialogMethod<CharacterDialoguePlugin> {

    public WaitMethod(CharacterDialoguePlugin main) {
        super("wait", main);
    }

    @Override
    public void execute(Player player, String arg, DialogSession session) {
        long seconds = Long.parseLong(arg);
        int next = session.getCurrentIndex() + 1;
        session.cancel();

        Bukkit.getScheduler().runTaskLater(getProvider(), () -> {
            if (next < session.getLines().size() && (player != null && player.isOnline())) {
                session.start(next);
            } else {
                session.cancel();
                session.destroy();
            }
        }, seconds * 20);
    }
}
