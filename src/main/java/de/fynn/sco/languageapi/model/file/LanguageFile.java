package de.fynn.sco.languageapi.model.file;

import de.fynn.sco.languageapi.model.exception.InvalidLanguageFileException;

import java.io.*;
import java.util.HashMap;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class LanguageFile {

    private final String identifier;
    private final String name;
    private final HashMap<String,String> translations = new HashMap<>();

    /*--------------------------------------------KONSTRUKTOREN-------------------------------------------------------*/

    /**
     * Der Konstruktor zum Erstellen von LanguageFile Objekten mit einer Datei
     * @param file Eine .language Datei, die alle Uebersetzunngen enthaelt
     * @throws InvalidLanguageFileException Weitergereichte Exception von der {@link #readFile(BufferedReader) readFile} Methode
     */
    public LanguageFile(File file) throws InvalidLanguageFileException {
        String tempIdentifier = null;
        String tempName = null;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] values = this.readFile(reader);
            tempIdentifier = values[0];
            tempName = values[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.identifier = tempIdentifier;
        this.name = tempName;
    }

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Gibt die Uebersetzung zu einem angegebenen Key zurueck
     * @param key Der Key des angeforderten Strings
     * @return der String, welcher unter dem Key zu finden ist
     */
    public String getTranslation(String key){
        return this.translations.get(key);
    }

    /**
     * Benutzt den BufferedReader um die .language-Datei einzulesen und den Identifier und die Uebersetzungen in den
     * jeweiligen Attributen abzulegen.
     * @param reader Der Reader, der die Datei beinhaltet
     * @return Der Name des Identifiers
     * @throws InvalidLanguageFileException Eine Exception wird ausgeloest, wenn kein Identifier vorhanden ist
     * oder wenn ein Key bzw. Value leer ist
     */
    private String[] readFile(BufferedReader reader) throws InvalidLanguageFileException{
        String tempIdentifier = null;
        String tempName = null;
        try {
            String line = reader.readLine();
            if (line == null) throw new InvalidLanguageFileException();
            tempIdentifier = line.split(" :: ")[0];
            tempName = line.split(" :: ")[1];
            while ( (line = reader.readLine()) != null ){
                String[] valuesFromString = line.split(" :: ");
                if (valuesFromString[0] == null || valuesFromString[1] == null) throw new InvalidLanguageFileException();
                this.translations.put(valuesFromString[0], valuesFromString[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{tempIdentifier, tempName};
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public String getIdentifier(){
        return this.identifier;
    }

    public String getName() {
        return name;
    }

}
