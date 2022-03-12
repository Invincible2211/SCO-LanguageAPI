package de.fynn.sco.languageapi.model.exception;

import de.fynn.sco.languageapi.model.interfaces.Strings;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class InvalidLanguageFileException extends Exception{

    /**
     * Dem Konstruktor der Superklasse wird die Exceptionnachricht uebergeben.
     */
    public InvalidLanguageFileException(){
        super(Strings.INVALID_LANGUAGE_FILE_EXCEPTION);
    }

}
