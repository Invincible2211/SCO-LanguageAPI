package de.fynn.mystic.mysticlanguageapi.language;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageFileLoader {

    public static Language loadLanguageFromFile(File file){
        Language lang = new Language(new HashMap<>());
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        Map<String,Object> messages = fileConfiguration.getValues(true);
        for (String s:
                messages.keySet()) {
            if(messages.get(s) instanceof String){
                lang.addValue(s,""+messages.get(s));
            }
        }
        return lang;
    }

}
