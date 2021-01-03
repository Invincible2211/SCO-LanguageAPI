package de.fynn.mystic.mysticlanguageapi;

import de.fynn.mystic.mysticlanguageapi.command.LanguageCommand;
import de.fynn.mystic.mysticlanguageapi.language.Language;
import de.fynn.mystic.mysticlanguageapi.language.LanguageManager;
import de.fynn.mystic.mysticlanguageapi.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MysticLanguageAPI extends JavaPlugin {

    private static MysticLanguageAPI instance;

    @Override
    public void onEnable() {
        instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(),this);

        getCommand("language").setExecutor(new LanguageCommand());
    }

    @Override
    public void onDisable() {

    }

    public static MysticLanguageAPI getInstance() {
        return instance;
    }

}
