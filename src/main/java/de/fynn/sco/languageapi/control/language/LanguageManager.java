package de.fynn.sco.languageapi.control.language;

import de.fynn.sco.languageapi.control.database.DBConnector;
import de.fynn.sco.languageapi.control.file.CFGLoader;
import de.fynn.sco.languageapi.model.file.LanguageFile;
import de.fynn.sco.languageapi.model.language.Language;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LanguageManager {

    private static final DBConnector connector;
    private static final HashMap<Plugin,HashMap<String, Language>> languages = new HashMap<>();
    private static final HashMap<Plugin,String> defaultLanguageFromPlugin = new HashMap<>();
    private static final List<String> availableLang = new ArrayList<>();
    private static final HashMap<UUID,String> playerLang = new HashMap<>();
    private static String defaultLang;
    private final String pluginDefaultLang;
    private final Plugin parent;
    private static LanguageManager instance;

    static {
        defaultLang = CFGLoader.getDefaultLang();
        connector = new DBConnector();
        List<UUID> players = connector.getRegisteredPlayers();
        for (UUID uuid:
             players) {
            playerLang.put(uuid, connector.loadPlayer(uuid));
        }
    }

    public LanguageManager(Plugin parent, String defaultLanguage, File defaultLang){
        this.parent = parent;
        pluginDefaultLang = defaultLanguage;
        defaultLanguageFromPlugin.put(parent,defaultLanguage);
        languages.put(parent,new HashMap<>());
        languages.get(parent).put(defaultLanguage, LanguageFileLoader.loadLanguageFromFile(defaultLang));
        loadParentLanguages();
    }

    public void registerLanguage(String name, File language){
        languages.get(parent).put(name,LanguageFileLoader.loadLanguageFromFile(language));
        if (!availableLang.contains(name))availableLang.add(name);
    }

    public List<String> getAvaiableLanguages(){
        return availableLang;
    }

    public String getMessage(UUID uuid, String path){
        String language = playerLang.get(uuid);
        if(language == null)language = defaultLang;
        if(languages.get(parent).containsKey(language)){
            return languages.get(parent).get(language).getValue(path);
        }else {
            return languages.get(parent).get(pluginDefaultLang).getValue(path);
        }
    }

    public String getMessage(Plugin plugin,UUID uuid, String path){
        String language = playerLang.get(uuid);
        if(language == null)language = defaultLang;
        if(languages.get(plugin).containsKey(language)){
            return languages.get(plugin).get(language).getValue(path);
        }else {
            return languages.get(plugin).get(defaultLanguageFromPlugin.get(plugin)).getValue(path);
        }
    }

    public String getPlayerLanguageAsString(UUID uuid){
        return playerLang.get(uuid);
    }

    public static void registerPlayer(UUID uuid){
        if(playerLang.containsKey(uuid))return;
        playerLang.put(uuid,defaultLang);
        connector.insertPlayer(uuid,defaultLang);
    }

    public void setLanguage(UUID uuid, String language){
        playerLang.replace(uuid, language);
        connector.updatePlayer(uuid, language);
    }

    public boolean containsLanguage(String language){
        return availableLang.contains(language);
    }

    private void loadParentLanguages(){
        File langFolder = new File(parent.getDataFolder()+"/lang");
        File[] langFiles = langFolder.listFiles();
        for (File langFile:
             langFiles) {
            if(langFile.getName().replaceFirst(".yml","")!=pluginDefaultLang){
                registerLanguage(langFile.getName().replaceFirst(".yml",""), langFile);
            }
        }
    }

    public static LanguageManager getInstance() {
        return instance;
    }

    public void registerPlugin(Plugin plugin){

    }

    public String getTranslation(Plugin plugin, UUID uuid, String message){

    }

    public void registerLanguage(Plugin plugin, LanguageFile languageFile){

    }

}
