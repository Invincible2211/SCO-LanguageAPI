package de.fynn.mystic.mysticlanguageapi.file;

import de.fynn.mystic.mysticlanguageapi.MysticLanguageAPI;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class CFGLoader {

    private final static FileConfiguration cfg = MysticLanguageAPI.getInstance().getConfig();

    public static HashMap<Integer,String> getDE(){
        HashMap<Integer,String> messages = new HashMap<>();
        messages.put(0,cfg.getString("messages.onlyPlayers.de"));
        messages.put(1,cfg.getString("messages.commandNotFound.de"));
        messages.put(2,cfg.getString("messages.languageNotFound.de"));
        messages.put(3,cfg.getString("messages.switchLanguage.de"));
        return messages;
    }

    public static HashMap<Integer,String> getENG(){
        HashMap<Integer,String> messages = new HashMap<>();
        messages.put(0,cfg.getString("messages.onlyPlayers.en"));
        messages.put(1,cfg.getString("messages.commandNotFound.en"));
        messages.put(2,cfg.getString("messages.languageNotFound.en"));
        messages.put(3,cfg.getString("messages.switchLanguage.en"));
        return messages;
    }

    public static String getDefaultLang(){
        return cfg.getString("defaultLanguage");
    }

    public static String[] getDBData(){
        return new String[]{
                cfg.getString("database.schema"),
                cfg.getString("database.ip"),
                cfg.getString("database.port"),
                cfg.getString("database.user"),
                cfg.getString("database.password")
        };
    }

}
