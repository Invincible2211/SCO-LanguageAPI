package de.fynn.mystic.mysticlanguageapi.listener;

import de.fynn.mystic.mysticlanguageapi.language.LanguageManager;
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
