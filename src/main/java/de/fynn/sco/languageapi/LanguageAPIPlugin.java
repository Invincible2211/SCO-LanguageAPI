package de.fynn.sco.languageapi;

import de.fynn.sco.languageapi.control.api.LanguageAPI;
import de.fynn.sco.languageapi.control.command.LanguageCommand;
import de.fynn.sco.languageapi.control.file.ConfigurationLoader;
import de.fynn.sco.languageapi.control.language.LanguageManager;
import de.fynn.sco.languageapi.control.listener.JoinListener;
import de.fynn.sco.languageapi.model.exception.InvalidLanguageFileException;
import de.fynn.sco.languageapi.model.interfaces.Strings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public final class LanguageAPIPlugin extends JavaPlugin {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private static LanguageAPIPlugin plugin;

    private  LanguageAPI apiInstance;

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Startup Logik des Plugins
     */
    @Override
    public void onEnable() {

        //initialize plugin
        plugin = this;
        this.setup();

        //register events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(),this);

        //register commands
        LanguageCommand languageCommand = new LanguageCommand();
        this.getCommand("language").setExecutor(languageCommand);
        this.getCommand("language").setTabCompleter(languageCommand.getTabComplete());

        //register this Plugin in the API
        try {
            this.apiInstance = new LanguageAPI(this, ConfigurationLoader.getDefaultLangFile());
        } catch (InvalidLanguageFileException e) {
            e.printStackTrace();
            pluginManager.disablePlugin(this);
        }

    }

    /**
     * Diese Mehtode wird beim deaktivieren des Plugins ausgefuehrt.
     */
    @Override
    public void onDisable() {

    }

    /**
     * Diese Mehtode ueberprueft, ob der Plugin Ordner schon erzeugt wurde. Ist dieser nicht vorhanden,
     * so wird dieser erzeugt und benötigten Dateien werden aus der Jar in das Verzeichnis kopiert.
     */
    private void setup(){
        File folder = new File(getDataFolder() + "/" + Strings.PARENT_FOLDER_NAME);
        if(!folder.exists()){
            try {
                Files.createDirectory(folder.getAbsoluteFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.loadAndSaveLanguageFileFromResource(Strings.DE_LANGUAGE_FILE);
            this.loadAndSaveLanguageFileFromResource(Strings.EN_LANGUAGE_FILE);
        }
    }

    /**
     * Mit dieser Methode können Dateien aus der Jar geladen und als unkomprimierte Dateien
     * im Ordner des Plugins gespeichert werden.
     * @param filename Der Name der Datei, die im Pluginordner verfuegbar sein soll
     */
    private void loadAndSaveLanguageFileFromResource(String filename){
        FileConfiguration langFile = YamlConfiguration.loadConfiguration(
                new InputStreamReader(LanguageAPIPlugin.class.getClassLoader().getResourceAsStream(filename))); //TODO ueberpruefen, ob nicht .yml Dateien auch geladen werden koennen
        try {
            langFile.save(new File(getDataFolder().getPath() + Strings.PARENT_FOLDER_NAME + "/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public static LanguageAPIPlugin getPlugin() {
        return plugin;
    }

    public LanguageAPI getApiInstance() {
        return this.apiInstance;
    }

}
