package de.fynn.sco.languageapi.control.listener;

import de.fynn.sco.languageapi.control.language.LanguageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
    public void onPlayerJoin(PlayerJoinEvent event){
        if(event.getPlayer().hasPlayedBefore()){
            languageManager.registerPlayer(event.getPlayer().getUniqueId());
        }
    }

}
