package me.iatog.characterdialogue.listeners;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.session.ChoiceSession;
import me.iatog.characterdialogue.session.DialogSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final CharacterDialoguePlugin main;

    public PlayerQuitListener(CharacterDialoguePlugin main) {
        this.main = main;
    }

    @EventHandler
    public void cancelDialogue(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Map<UUID, DialogSession> cache = main.getCache().getDialogSessions();

        if (!cache.containsKey(player.getUniqueId())) {
            return;
        }

        DialogSession session = cache.remove(player.getUniqueId());
        session.cancel();
    }

    @EventHandler
    public void cancelChoice(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Map<UUID, ChoiceSession> sessions = main.getCache().getChoiceSessions();

        if (!sessions.containsKey(player.getUniqueId())) {
            return;
        }

        sessions.remove(player.getUniqueId());
    }

}
