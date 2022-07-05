package me.iatog.characterdialogue.dialogs.method;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.dialogs.DialogMethod;
import me.iatog.characterdialogue.session.DialogSession;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class SoundMethod extends DialogMethod<CharacterDialoguePlugin> {

    public SoundMethod(CharacterDialoguePlugin main) {
        super("sound", main);
    }

    @Override
    public void execute(Player player, String arg, DialogSession session) {
        String[] part = arg.split(",");
        Sound sound;

        try {
            sound = Sound.valueOf(part[0]);
        } catch (Exception exception) {
            getProvider().getLogger().log(Level.SEVERE, "Unknown sound \"" + part[0] + "\", stopping dialogue.", exception);
            session.destroy();
            return;
        }

        float volume = def(part, 1);
        float pitch = def(part, 2);

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    private float def(String[] value, int index) {
        if (index >= value.length) {
            return (float) 1;
        } else {
            return isInt(value[index]) ? Float.parseFloat(value[index]) : (float) 1;
        }
    }

    private boolean isInt(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
