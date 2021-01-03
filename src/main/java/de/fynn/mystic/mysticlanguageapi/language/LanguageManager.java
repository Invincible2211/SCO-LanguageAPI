package de.fynn.mystic.mysticlanguageapi.language;

import de.fynn.mystic.mysticlanguageapi.file.CFGLoader;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LanguageManager {

    private static final HashMap<Plugin,HashMap<String,Language>> languages = new HashMap<>();
    private static final HashMap<Plugin,String> defaultLanguageFromPlugin = new HashMap<>();
    private static final List<String> availableLang = new ArrayList<>();
    private static final HashMap<UUID,String> playerLang = new HashMap<>();
    private static String defaultLang;
    private final String pluginDefaultLang;
    private final Plugin parent;

    static {
        defaultLang = CFGLoader.getDefaultLang();
    }

    public LanguageManager(Plugin parent, String defaultLanguage, Language defaultLang){
        this.parent = parent;
        pluginDefaultLang = defaultLanguage;
        defaultLanguageFromPlugin.put(parent,defaultLanguage);
        languages.put(parent,new HashMap<>());
        languages.get(parent).put(defaultLanguage,defaultLang);
    }

    public void registerLanguage(String name, Language language){
        languages.get(parent).put(name,language);
        if (!availableLang.contains(name))availableLang.add(name);
    }

    public List<String> getAvaiableLanguages(){
        return availableLang;
    }

    public String getMessage(UUID uuid, int value){
        String language = playerLang.get(uuid);
        if(language == null)language = defaultLang;
        if(languages.get(parent).containsKey(language)){
            return languages.get(parent).get(language).getValue(value);
        }else {
            return languages.get(parent).get(pluginDefaultLang).getValue(value);
        }
    }

    public String getMessage(Plugin plugin,UUID uuid, int value){
        String language = playerLang.get(uuid);
        if(language == null)language = defaultLang;
        if(languages.get(plugin).containsKey(language)){
            return languages.get(plugin).get(language).getValue(value);
        }else {
            return languages.get(plugin).get(defaultLanguageFromPlugin.get(plugin)).getValue(value);
        }
    }

    public String getPlayerLanguageAsString(UUID uuid){
        return playerLang.get(uuid);
    }

    public static void registerPlayer(UUID uuid){
        if(playerLang.containsKey(uuid))return;
        playerLang.put(uuid,defaultLang);
    }

    public void setLanguage(UUID uuid, String language){
        playerLang.replace(uuid, language);
    }

    public boolean containsLanguage(String language){
        return availableLang.contains(language);
    }

}
