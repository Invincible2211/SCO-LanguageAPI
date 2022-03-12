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
    private final HashMap<String,String> translations = new HashMap<>();

    /*--------------------------------------------KONSTRUKTOREN-------------------------------------------------------*/

    /**
     * Der Konstruktor zum Erstellen von LanguageFile Objekten mit einer Datei
     * @param file Eine .language Datei, die alle Uebersetzunngen enthaelt
     * @throws InvalidLanguageFileException Weitergereichte Exception von der {@link #readFile(BufferedReader) readFile} Methode
     */
    public LanguageFile(File file) throws InvalidLanguageFileException {
        String tempIdentifier = null;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            tempIdentifier = this.readFile(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.identifier = tempIdentifier;
    }

    /**
     * Der Konstruktor zum Erstellen von LanguageFile Objekten mit einem Stream
     * @param inputStream Ein Stream der eine .language Datei beinhaltet, die alle Uebersetzunngen enthaelt
     * @throws InvalidLanguageFileException Weitergereichte Exception von der {@link #readFile(BufferedReader) readFile} Methode
     */
    public LanguageFile(InputStream inputStream) throws InvalidLanguageFileException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        this.identifier = this.readFile(reader);
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
    private String readFile(BufferedReader reader) throws InvalidLanguageFileException{
        String tempIdentifier = null;
        try {
            String line = reader.readLine();
            if (line == null) throw new InvalidLanguageFileException();
            tempIdentifier = line;
            while ( (line = reader.readLine()) != null ){
                String[] valuesFromString = line.split(" :: ");
                if (valuesFromString[0] == null || valuesFromString[1] == null) throw new InvalidLanguageFileException();
                this.translations.put(valuesFromString[0], valuesFromString[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempIdentifier;
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public String getIdentifier(){
        return this.identifier;
    }

}
