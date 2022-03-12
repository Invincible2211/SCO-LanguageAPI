package de.fynn.sco.languageapi.control.file;

import de.fynn.sco.languageapi.LanguageAPIPlugin;
import de.fynn.sco.languageapi.model.DatabaseData;
import de.fynn.sco.languageapi.model.interfaces.ConfigurationFileKeys;
import de.fynn.sco.languageapi.model.interfaces.Strings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class ConfigurationLoader {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final static FileConfiguration config = LanguageAPIPlugin.getPlugin().getConfig();

    /*--------------------------------------------INITIALIZER---------------------------------------------------------*/

    /**
     * Der static Initializer kopiert die config-Datei in den Plugin-Ordner.
     */
    static {
        config.options().copyDefaults(true);
        LanguageAPIPlugin.getPlugin().saveConfig();
    }

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Diese Methode gibt die in der Config-Datei eingestellte Standartsprache zurueck.
     * @return Die aktuelle Standartsprache
     */
    public static String getDefaultLang(){
        return config.getString("defaultLanguage");
    }

    /**
     * Diese Methode gibt eine Datei zurueck, welche die in der Config eingestellte Standartsprache repraesentiert.
     * Ist in der Config ein fehlerhafter Wert eingestellt (die Sprache exestiert nicht),
     * wird das Plugin disabled.
     * @return Eine Datei, die alle Uebersetzungen dieses Plugins enthaelt
     */
    public static File getDefaultLangFile(){
        File languageFile = new File( LanguageAPIPlugin.getPlugin().getDataFolder() + Strings.PARENT_FOLDER_NAME + "/" + getDefaultLang() + ".lang");
        if (languageFile.exists()){
            return languageFile;
        } else {
            LanguageAPIPlugin.getPlugin().getLogger().warning(Strings.DEFAULT_LANGUAGE_FILE_DOES_NOT_EXISTS);
            Bukkit.getPluginManager().disablePlugin(LanguageAPIPlugin.getPlugin());
            return null;
        }
    }

    /**
     * Diese Methode gibt alle Werte, die fuer den Datenbank-Connector wichtig sind als Record zurueck
     * @return Ein Record mit allen Werten fuer die Datenbank
     */
    public static DatabaseData getDatabaseData(){
        return new DatabaseData(config.getString(ConfigurationFileKeys.DATABASE_SCHEMA),
                config.getString(ConfigurationFileKeys.DATABASE_IP_ADRESS),
                config.getString(ConfigurationFileKeys.DATABASE_PORT),
                config.getString(ConfigurationFileKeys.DATABASE_USERNAME),
                config.getString(ConfigurationFileKeys.DATABASE_PASSWORD));
    }

}
