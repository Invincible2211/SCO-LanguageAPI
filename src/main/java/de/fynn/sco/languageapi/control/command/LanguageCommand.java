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
public class LanguageCommand implements CommandExecutor {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final LanguageAPI languageAPI = LanguageAPIPlugin.getPlugin().getApiInstance();

    private final LanguageManager languageManager = LanguageManager.getInstance();

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Der command kann von Spielern verwendet werden, um ihre bevorzugt Sprache festzulegen
     * @param sender Der Spieler, der den Command ausgefuehrt hat (kann auch die Konsole sein, diese wird aber abgewiesen)
     * @param command Der language Command selbst
     * @param label Das label des Commands, das benutzt wurde, um diesen aufzurufen
     * @param args Alle weiteren Argumente, die dem Command uebergeben wurden
     * @return Wahrheitswert, ob der Command erfolgreiche ausgefuehrt werden konnte
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length!=0){
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    builder.append(args[i]);
                    if (i < args.length-1){
                        builder.append(" ");
                    }
                }
                String language = builder.toString();
                if(languageManager.containsLanguage(language)){
                    languageManager.setLanguage(player.getUniqueId(),language);
                    languageAPI.sendPlayerMessageById(player.getUniqueId(), MessageKeys.COMMAND_CHANGE_LANGUAGE);
                }else {
                    languageAPI.sendPlayerMessageById(player.getUniqueId(),MessageKeys.COMMAND_LANGUAGE_NOT_FOUND);
                }
            }else {
                languageAPI.sendPlayerMessageById(player.getUniqueId(), MessageKeys.LANGUAGE_INFO
                        + languageManager.getPlayerLanguageName(player.getUniqueId()));
                return false;
            }
        }else {
            sender.sendMessage(Strings.COMMAND_ONLY_FOR_PLAYERS);
        }
        return true;
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public LanguageCommandTabComplete getTabComplete(){
        return new LanguageCommandTabComplete();
    }

    /*-----------------------------------END OF CLASS LANGUAGE_COMMAND------------------------------------------------*/

    private class LanguageCommandTabComplete implements TabCompleter {

        /**
         * Hiermit koennen Spieler durch das drucken von Tab mit dem language Befehl die Sprachen
         * automatisch vervollstaendigen lassen.
         * @param commandSender Der Spieler oder eine Konsole, die das Argument des langugae
         *                      Befehls verfollstaendigen mochete
         * @param command Der betroffende Befehl, hier immer der language Befehl
         * @param label Das Label, welches fuer den Befehlsaufruf verwendet wurde
         * @param args Die bisher eingegebenen Argumente fuer den Befehl
         * @return Eine alphabetisch sortierte Liste fuer moegliche Argument-Werte
         */
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
            List<String> completions = new ArrayList<>();
            List<String> mappedLanguages = new ArrayList<>();
            for (String languageCode:
                 languageManager.getAvailableLanguage()) {
                mappedLanguages.add(languageManager.mapLanguageCode(languageCode));
            }
            StringUtil.copyPartialMatches(args[0], mappedLanguages, completions);
            Collections.sort(completions);
            return completions;
        }

    }

}
