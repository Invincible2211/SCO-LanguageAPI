package de.fynn.sco.languageapi;

import de.fynn.sco.languageapi.command.LanguageCommand;
import de.fynn.sco.languageapi.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public final class LanguageAPI extends JavaPlugin {

    private static LanguageAPI instance;

    @Override
    public void onEnable() {
        instance = this;
        setup();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(),this);
        getCommand("language").setExecutor(new LanguageCommand());
    }

    @Override
    public void onDisable() {

    }

    public static LanguageAPI getInstance() {
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
            ClassLoader classLoader = LanguageAPI.class.getClassLoader();
            FileConfiguration langFile = YamlConfiguration.loadConfiguration(new InputStreamReader(classLoader.getResourceAsStream("defaultMessages.yml")));
            try {
                langFile.save(new File(folder.getPath()+"/defaultMessages.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            langFile = YamlConfiguration.loadConfiguration(new InputStreamReader(classLoader.getResourceAsStream("de_DE.yml")));
            try {
                langFile.save(new File(folder.getPath()+"/de_DE.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            langFile = YamlConfiguration.loadConfiguration(new InputStreamReader(classLoader.getResourceAsStream("en_EN.yml")));
            try {
                langFile.save(new File(folder.getPath()+"/en_EN.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
