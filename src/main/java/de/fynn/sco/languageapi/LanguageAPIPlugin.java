package de.fynn.sco.languageapi;

import de.fynn.sco.languageapi.control.command.LanguageCommand;
import de.fynn.sco.languageapi.control.language.LanguageManager;
import de.fynn.sco.languageapi.control.listener.JoinListener;
import de.fynn.sco.languageapi.model.exception.InvalidLanguageFileException;
import de.fynn.sco.languageapi.model.file.LanguageFile;
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

public final class LanguageAPIPlugin extends JavaPlugin {

    private static LanguageAPIPlugin instance;

    @Override
    public void onEnable() {

        //initialize plugin
        instance = this;
        setup();
        new LanguageManager();

        //register plugin in LanguageManger
        try {
            LanguageManager.getInstance().registerPlugin(this, new LanguageFile(new File (getDataFolder().getPath() + Strings.PARENT_FOLDER_NAME + Strings.DE_LANGUAGE_FILE)));
            LanguageManager.getInstance().registerLanguage(this, new LanguageFile(new File (getDataFolder().getPath() + Strings.PARENT_FOLDER_NAME + Strings.EN_LANGUAGE_FILE)));
        } catch (InvalidLanguageFileException e) {
            e.printStackTrace();
        }

        //register events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(),this);

        //register commands
        getCommand("language").setExecutor(new LanguageCommand());

    }

    @Override
    public void onDisable() {

    }

    public static LanguageAPIPlugin getInstance() {
        return instance;
    }

    private void setup(){
        File folder = new File(getDataFolder()+"/language");
        if(!folder.exists()){
            try {
                Files.createDirectory(folder.getAbsoluteFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadAndSaveLanguageFileFromResource(Strings.DE_LANGUAGE_FILE);
            loadAndSaveLanguageFileFromResource(Strings.EN_LANGUAGE_FILE);
        }
    }

    private void loadAndSaveLanguageFileFromResource(String filename){
        FileConfiguration langFile = YamlConfiguration.loadConfiguration(new InputStreamReader(LanguageAPIPlugin.class.getClassLoader().getResourceAsStream(filename)));
        try {
            langFile.save(new File(getDataFolder().getPath() + Strings.PARENT_FOLDER_NAME + "/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
