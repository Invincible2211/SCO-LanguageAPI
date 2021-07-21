package de.fynn.mystic.mysticlanguageapi.command;

import de.fynn.mystic.mysticlanguageapi.MysticLanguageAPI;
import de.fynn.mystic.mysticlanguageapi.file.CFGLoader;
import de.fynn.mystic.mysticlanguageapi.language.Language;
import de.fynn.mystic.mysticlanguageapi.language.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStreamReader;

public class LanguageCommand implements CommandExecutor {

    private LanguageManager languageManager;

    {
        languageManager = new LanguageManager(MysticLanguageAPI.getInstance(),CFGLoader.getDefaultLang(),new File(MysticLanguageAPI.getInstance().getDataFolder()+"/language/defaultMessages.yml"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==1){
                if(languageManager.containsLanguage(args[0])){
                    languageManager.setLanguage(p.getUniqueId(),args[0]);
                    p.sendMessage(languageManager.getMessage(p.getUniqueId(),"COMMAND.CHANGE_LANGUAGE"));
                }else {
                    p.sendMessage(languageManager.getMessage(p.getUniqueId(),"COMMAND.LANGUAGE_NOT_FOUND"));
                }
            }else {
                p.sendMessage(languageManager.getMessage(p.getUniqueId(),"COMMAND.COMMAND_NOT_FOUND"));
                return false;
            }
        }else {
            sender.sendMessage(languageManager.getMessage(null,"COMMAND.ONLY_FOR_PLAYERS"));
        }
        return true;
    }

}
