package de.fynn.sco.languageapi.control.file;

import de.fynn.sco.languageapi.LanguageAPIPlugin;
import de.fynn.sco.languageapi.model.DatabaseData;
import de.fynn.sco.languageapi.model.interfaces.ConfigurationFileKeys;
import org.bukkit.configuration.file.FileConfiguration;

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
