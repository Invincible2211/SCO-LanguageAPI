package de.fynn.sco.languageapi.control.database;

import de.fynn.sco.languageapi.LanguageAPIPlugin;
import de.fynn.sco.languageapi.control.file.ConfigurationLoader;
import de.fynn.sco.languageapi.model.DatabaseData;
import de.fynn.sco.languageapi.model.interfaces.Strings;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class DatabaseConnector {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private Connection connection;
    private final DatabaseData databaseData;

    private static DatabaseConnector instance;

    static {
        try {
            instance = new DatabaseConnector();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prepared Statements
    private final PreparedStatement createSchema;
    private final PreparedStatement createTable;
    private final PreparedStatement insertPlayer;
    private final PreparedStatement updatePlayer;
    private final PreparedStatement alreadyExists;
    private final PreparedStatement loadPlayer;
    private final PreparedStatement getRegisteredPlayers;

    /*--------------------------------------------KONSTRUKTOREN-------------------------------------------------------*/

    /**
     * Der private Konstruktor baut eine Verbindung mit der Datenbank auf und bereitet die SQL-Statements vor.
     * @throws SQLException Gibt auftretende SQL-Exceptions weiter
     */
    private DatabaseConnector() throws SQLException{
        databaseData = ConfigurationLoader.getDatabaseData();
        try {
            connect();
        } catch (SQLException e) {
            LanguageAPIPlugin.getPlugin().getLogger().warning(Strings.DATABASE_CONNECTION_FAILED);
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(LanguageAPIPlugin.getPlugin());
        }
        createSchema = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS " + databaseData.schema() + ";");
        createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + databaseData.schema()
                + ".language (uuid VARCHAR(150) NOT NULL, language VARCHAR(150) NOT NULL ,PRIMARY KEY (uuid));");
        createSchema.execute();
        createTable.execute();
        insertPlayer = connection.prepareStatement("INSERT INTO " + databaseData.schema() + ".language (uuid,language) VALUES (?,?);");
        updatePlayer = connection.prepareStatement("UPDATE " + databaseData.schema() + ".language SET language = ? WHERE uuid = ?;");
        alreadyExists = connection.prepareStatement("SELECT * FROM " + databaseData.schema() + ".language WHERE uuid = ?;");
        loadPlayer = connection.prepareStatement("SELECT language FROM " + databaseData.schema() + ".language WHERE uuid = ?;");
        getRegisteredPlayers = connection.prepareStatement("SELECT uuid FROM " + databaseData.schema() + ".language");
    }

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Diese Methode baut eine Verbindung mit der Datenbank auf.
     * @throws SQLException Gibt auftretende SQL-Exceptions weiter
     */
    private void connect() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://"+databaseData.ipAdress()+":"+databaseData.port()+"?useSSL=false",databaseData.username(), databaseData.password());
    }

    /**
     * Die Methode fuegt einen neuen Spieler in die Datenbank ein.
     * @param uuid Die UUID des neuen Spielers
     * @param language Die Sprache, die dem Spieler zugeordnet werden soll
     */
    public void insertPlayer(UUID uuid, String language){
        try {
            insertPlayer.setString(1, uuid.toString());
            insertPlayer.setString(2, language);
            insertPlayer.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Hiermit kann die Sprache eine Spielers in der Datenbank geaendert werden
     * @param uuid Die UUID des Spielers
     * @param newLangauge Die neue Sprache
     */
    public void updatePlayer(UUID uuid, String newLangauge){
        try {
            updatePlayer.setString(1, uuid.toString());
            updatePlayer.setString(2, newLangauge);
            updatePlayer.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Die Methode ueberprueft, ob ein Spieler schon in der Datenbank vorhanden ist
     * @param uuid Die UUID des Spielers
     * @return Wahrheitswert, ob der Spieler schon vorhanden ist
     */
    public boolean alreadyExists(UUID uuid){
        try {
            alreadyExists.setString(1, uuid.toString());
            return alreadyExists.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Die Methode gibt zurueck, welche Sprache der Spieler eingestellt hat.
     * @param uuid Die UUID des Spielers
     * @return Die Sprache, die der Spieler eingestellt hat
     */
    public String loadPlayer(UUID uuid){
        try {
            loadPlayer.setString(1, uuid.toString());
            ResultSet result = loadPlayer.executeQuery();
            result.next();
            return result.getString(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Die Methode gibt eine Liste aller Spieler zurueck, die in der Datenbank registriert sind
     * @return Eine Liste aller bereits registrierten Spieler
     */
    public List<UUID> getRegisteredPlayers(){
        List<UUID> players = new ArrayList<>();
        try {
            ResultSet result = getRegisteredPlayers.executeQuery();
            while (result.next()){
                players.add(UUID.fromString(result.getString(1)));
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return players;
    }

    /*-----------------------------------------GETTER AND SETTER------------------------------------------------------*/

    public static DatabaseConnector getInstance() {
        return instance;
    }

}
