package de.fynn.sco.languageapi.model;

/**
 * @author Freddyblitz
 * @version 1.0
 *
 * Dieser Record repraesentiert die Daten, die zum Arbeiten mit einer Datenbank benoetigt werden.
 */
public record DatabaseData(String schema, String ipAdress, String port, String username, String password) {

}
