package de.fynn.sco.languageapi.control.listener;

import de.fynn.sco.languageapi.control.language.LanguageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(event.getPlayer().hasPlayedBefore()){
            LanguageManager.registerPlayer(event.getPlayer().getUniqueId());
        }
    }

}
