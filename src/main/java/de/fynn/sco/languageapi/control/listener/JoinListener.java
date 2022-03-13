package de.fynn.sco.languageapi.control.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.GamePhase;
import de.fynn.sco.languageapi.LanguageAPIPlugin;
import de.fynn.sco.languageapi.control.language.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class JoinListener implements Listener {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final LanguageManager languageManager = LanguageManager.getInstance();


    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Sobald ein Spieler dem Spiel joint, der zuvor noch nicht gespielt hat, wird dieser
     * beim LanguageManager registriert.
     * @param event das JoinEvent, welches ausgeloest wird, sobald ein Spieler auf den Server joint
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            languageManager.registerPlayer(event.getPlayer());
        }
    }

}
