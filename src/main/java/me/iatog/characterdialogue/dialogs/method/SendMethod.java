package me.iatog.characterdialogue.dialogs.method;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.dialogs.DialogMethod;
import me.iatog.characterdialogue.session.DialogSession;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SendMethod extends DialogMethod<CharacterDialoguePlugin> {

    public SendMethod() {
        super("send");
    }

    @Override
    public void execute(Player player, String arg, DialogSession session) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', arg));
    }

}
