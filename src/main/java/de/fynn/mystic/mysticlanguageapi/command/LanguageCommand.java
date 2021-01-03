package de.fynn.mystic.mysticlanguageapi.command;

import de.fynn.mystic.mysticlanguageapi.MysticLanguageAPI;
import de.fynn.mystic.mysticlanguageapi.file.CFGLoader;
import de.fynn.mystic.mysticlanguageapi.language.Language;
import de.fynn.mystic.mysticlanguageapi.language.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageCommand implements CommandExecutor {

    private LanguageManager languageManager;

    {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==1){
                if(languageManager.containsLanguage(args[0])){
                    languageManager.setLanguage(p.getUniqueId(),args[0]);
                    p.sendMessage(languageManager.getMessage(p.getUniqueId(),3));
                }else {
                    p.sendMessage(languageManager.getMessage(p.getUniqueId(),2));
                }
            }else {
                p.sendMessage(languageManager.getMessage(p.getUniqueId(),1));
                return false;
            }
        }else {
            sender.sendMessage(languageManager.getMessage(null,0));
        }
        return true;
    }

    private void registerAPILang(){
        Language de_DE = new Language(CFGLoader.getDE());
        Language en_EN = new Language(CFGLoader.getENG());
        if(CFGLoader.getDefaultLang().equals("de_DE")){
            languageManager = new LanguageManager(MysticLanguageAPI.getInstance(),"de_DE",de_DE);
            languageManager.registerLanguage("en_EN",en_EN);
        }else {
            languageManager = new LanguageManager(MysticLanguageAPI.getInstance(),"en_EN",en_EN);
            languageManager.registerLanguage("de_DE",de_DE);
        }
    }

}
