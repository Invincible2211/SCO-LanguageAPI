package de.fynn.sco.languageapi.control.api;

import de.fynn.sco.languageapi.control.language.LanguageManager;
import de.fynn.sco.languageapi.model.exception.InvalidLanguageFileException;
import de.fynn.sco.languageapi.model.file.LanguageFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class LanguageAPI {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final Plugin parent;

    private final static LanguageManager languageManager = LanguageManager.getInstance();

    /*--------------------------------------------KONSTRUKTOREN-------------------------------------------------------*/

    /**
     * Der Konstruktor benoetigt das Plugin, fuer welches die Instanz der LanguageAPI erstellt wird,
     * um Anfragen fuer uebersetzungen den richtigen LanguageFiles zuordnen zu koennen. Ausserdem wird
     * zur instanzierung mindestens ein LanguageFile benoetigt.
     * Dies ist unter anderem die standart Sprache des Plugins und wird verwendet, wenn ein Spieler eine Sprache eingestellt
     * hat, die von dem regestrierten Plugin nicht bereitgestellt wird.
     * @param parent das Plugin, welches die API benutzen moechte
     * @param defaultLanguage
     * @param languageFileFolderPath
     * @throws InvalidLanguageFileException
     */
    public LanguageAPI(Plugin parent, String defaultLanguage, String languageFileFolderPath) throws InvalidLanguageFileException {
        this.parent = parent;
        File parentFolder = new File(languageFileFolderPath);
        File[] files = parentFolder.listFiles();
        List<LanguageFile> languageFiles = new ArrayList<>();
        LanguageFile defaultLanguageFile = null;
        for (File file:
             files) {
            if (file.getName().contains(".language")){
                LanguageFile currentLangFile = new LanguageFile(file);
                if (currentLangFile.getIdentifier().equals(defaultLanguage)){
                    defaultLanguageFile = currentLangFile;
                } else {
                    languageFiles.add(currentLangFile);
                }
            }
        }
        languageManager.registerPlugin(parent, defaultLanguageFile);
        for (LanguageFile language:
             languageFiles) {
            languageManager.registerLanguage(parent, language);
        }
    }

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Gibt die ubersetzung fuer einen bestimmten String in der Sprache, die der betroffene Spieler eingestellt hat zurueck.
     * @param playerUUID Die UUID des Spielers, fuer den die Ubersetzung benoetigt wird.
     * @param messageKey Ein String, der einen Key in der Language-Datei repraesentiert
     * @return Die geforderte uberstzung fuer den Spieler wenn der Key exestiert, ansonsten null
     */
    public String getTranslation(UUID playerUUID, String messageKey){
        return languageManager.getTranslation(parent, playerUUID, messageKey);
    }

    /**
     * Sendet einem Spieler direkt das Ergebnis der {@link #getTranslation(UUID, String) getTranslation} Methode
     * @param playerUUID Die UUID des Spielers, der die Nachricht bekommen soll
     * @param messageKey Ein String, der einen Key in der Language-Datei repraesentiert
     * @return Wahrheitswert, ob die Nachricht gesendet werden konnte. Ist der Spieler nicht online oder exestiert nicht,
     * wird false zurueckgegeben.
     */
    public boolean sendPlayerMessageById(UUID playerUUID, String messageKey){
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null){
            player.sendMessage("[" + parent.getName() + "] " + this.getTranslation(playerUUID, messageKey));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Wird verwendet, um weitere unterstuetzte Sprachen hinzuzufuegen. Das File Objekt muss eine .language Datei sein.
     * @param languageFile die Language-Datei, in der die Sprache definiert ist
     * @throws InvalidLanguageFileException
     */
    private void registerLanguage(File languageFile) throws InvalidLanguageFileException {
        languageManager.registerLanguage(parent, new LanguageFile(languageFile));
    }

}
