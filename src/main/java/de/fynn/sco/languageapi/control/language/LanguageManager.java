package de.fynn.sco.languageapi.control.language;

import de.fynn.sco.languageapi.control.database.DatabaseConnector;
import de.fynn.sco.languageapi.control.file.CFGLoader;
import de.fynn.sco.languageapi.model.file.LanguageFile;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class LanguageManager {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final HashMap<UUID,String> playerLanguageHashMap = new HashMap<>();

    private HashMap<Plugin, HashMap<String,LanguageFile>> languageFiles = new HashMap<>();

    private final List<String> availableLanguage = new ArrayList<>();

    private String defaultLanguage;

    private final DatabaseConnector databaseConnector = new DatabaseConnector();

    private static LanguageManager instance;

    /*--------------------------------------------KONSTRUKTOREN-------------------------------------------------------*/

    public LanguageManager(){
        instance = this;
        this.init();
    }

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    public void registerPlayer(UUID uuid){
        if (this.databaseConnector.alreadyExists(uuid)){
            this.playerLanguageHashMap.put(uuid, this.databaseConnector.loadPlayer(uuid));
        } else {
            this.playerLanguageHashMap.put(uuid, this.defaultLanguage);
            this.databaseConnector.insertPlayer(uuid, this.defaultLanguage);
        }
    }

    public void registerPlugin(Plugin plugin, LanguageFile defaultLanguage){
        HashMap<String, LanguageFile> pluginLanguageFileHashMap = new HashMap<>();
        pluginLanguageFileHashMap.put(defaultLanguage.getIdentifier(), defaultLanguage);
        this.languageFiles.put(plugin,pluginLanguageFileHashMap);
        if (!this.containsLanguage(defaultLanguage.getIdentifier())){
            this.availableLanguage.add(defaultLanguage.getIdentifier());
        }
    }

    public void registerLanguage(Plugin plugin, LanguageFile languageFile){
        HashMap<String, LanguageFile> pluginLanguageFileHashMap = this.languageFiles.get(plugin);
        pluginLanguageFileHashMap.put(languageFile.getIdentifier(), languageFile);
        if (!this.containsLanguage(languageFile.getIdentifier())){
            this.availableLanguage.add(languageFile.getIdentifier());
        }
    }

    public void setLanguage(UUID uuid, String language){
        this.playerLanguageHashMap.replace(uuid, language);
        this.databaseConnector.updatePlayer(uuid, language);
    }

    public boolean containsLanguage(String language){
        return this.availableLanguage.contains(language);
    }

    public String getTranslation(Plugin plugin, UUID uuid, String message){
        return this.languageFiles.get(plugin).get(playerLanguageHashMap.get(uuid)).getTranslation(message);
    }

    private void init(){
        this.defaultLanguage = CFGLoader.getDefaultLang();
        for (UUID uuid:
                databaseConnector.getRegisteredPlayers()) {
            this.playerLanguageHashMap.put(uuid, databaseConnector.loadPlayer(uuid));
        }
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public static LanguageManager getInstance() {
        return instance;
    }

}
