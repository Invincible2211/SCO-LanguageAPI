package de.fynn.sco.languageapi.control.command;

import de.fynn.sco.languageapi.LanguageAPIPlugin;
import de.fynn.sco.languageapi.control.language.LanguageManager;
import de.fynn.sco.languageapi.model.interfaces.MessageKeys;
import de.fynn.sco.languageapi.model.interfaces.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class LanguageCommand implements CommandExecutor {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final LanguageManager languageManager = LanguageManager.getInstance();

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     *
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==1){
                if(languageManager.containsLanguage(args[0])){
                    languageManager.setLanguage(p.getUniqueId(),args[0]);
                    p.sendMessage(languageManager.getTranslation(LanguageAPIPlugin.getInstance(), p.getUniqueId(), MessageKeys.COMMAND_CHANGE_LANGUAGE));
                }else {
                    p.sendMessage(languageManager.getTranslation(LanguageAPIPlugin.getInstance(), p.getUniqueId(),MessageKeys.COMMAND_LANGUAGE_NOT_FOUND));
                }
            }else {
                p.sendMessage(languageManager.getTranslation(LanguageAPIPlugin.getInstance(), p.getUniqueId(),MessageKeys.COMMAND_COMMAND_NOT_FOUND));
                return false;
            }
        }else {
            sender.sendMessage(Strings.COMMAND_ONLY_FOR_PLAYERS);
        }
        return true;
    }

}
