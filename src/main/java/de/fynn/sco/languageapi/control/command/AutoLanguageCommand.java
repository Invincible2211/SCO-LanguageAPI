package de.fynn.sco.languageapi.control.command;

import de.fynn.sco.languageapi.LanguageAPIPlugin;
import de.fynn.sco.languageapi.control.api.LanguageAPI;
import de.fynn.sco.languageapi.control.language.LanguageManager;
import de.fynn.sco.languageapi.model.interfaces.MessageKeys;
import de.fynn.sco.languageapi.model.interfaces.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class AutoLanguageCommand implements CommandExecutor {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final LanguageAPI languageAPI = LanguageAPIPlugin.getPlugin().getApiInstance();

    private final LanguageManager languageManager = LanguageManager.getInstance();

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1){
                if (args[0].equals("true") || args[0].equals("false")){
                    languageManager.setPlayerAutoUpdateLanguage(player.getUniqueId(), Boolean.parseBoolean(args[0]));
                } else {
                    languageAPI.sendPlayerMessageById(player.getUniqueId(), MessageKeys.LANGUAGE_AUTO_CHANGE_INFO);
                }
            }else {
                languageAPI.sendPlayerMessageById(player.getUniqueId(), MessageKeys.LANGUAGE_AUTO_CHANGE_INFO);
            }
        }else {
            sender.sendMessage(Strings.COMMAND_ONLY_FOR_PLAYERS);
        }
        return true;
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public AutoLanguageCommand.LanguageCommandTabComplete getTabComplete(){
        return new LanguageCommandTabComplete();
    }

    /*-----------------------------------END OF CLASS LANGUAGE_COMMAND------------------------------------------------*/

    private class LanguageCommandTabComplete implements TabCompleter {

        /**
         * Hiermit koennen Spieler durch das drucken von Tab sich den autoLanguage Befehl vervollstaendigen lassen.
         * @param commandSender Der Spieler oder eine Konsole, die das Argument des autoLangugae
         *                      Befehls verfollstaendigen mochete
         * @param command Der betroffende Befehl, hier immer der autoLanguage Befehl
         * @param label Das Label, welches fuer den Befehlsaufruf verwendet wurde
         * @param args Die bisher eingegebenen Argumente fuer den Befehl
         * @return Eine alphabetisch sortierte Liste fuer moegliche Argument-Werte
         */
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
            List<String> completions = new ArrayList<>();
            List<String> availableStrings = new ArrayList<>();
            availableStrings.add("true");
            availableStrings.add("false");
            StringUtil.copyPartialMatches(args[0], availableStrings, completions);
            Collections.sort(completions);
            return completions;
        }

    }

}
