package de.fynn.mystic.mysticlanguageapi.database;

import de.fynn.mystic.mysticlanguageapi.file.CFGLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBConnector {

    private Connection connection;
    private Statement statement;
    private String[] db = CFGLoader.getDBData();

    {
        try {
            connect();
            statement.execute("CREATE SCHEMA IF NOT EXISTS "+db[0]+";");
            statement.execute("CREATE TABLE IF NOT EXISTS "+db[0]+".language (uuid VARCHAR(150) NOT NULL, language VARCHAR(150) NOT NULL ,PRIMARY KEY (uuid));");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void connect() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://"+db[1]+":"+db[2]+"?useSSL=false",db[3],db[4]);
        statement = connection.createStatement();
    }


    private void validateConnection(){
        try {
            if(connection==null||connection.isClosed()){
                connect();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insertPlayer(UUID uuid, String language){
        validateConnection();
        try {
            statement.execute("INSERT INTO "+db[0]+".language (uuid,language) VALUES ('"+uuid.toString()+"','"+language+"');");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updatePlayer(UUID uuid, String newLangauge){
        validateConnection();
        try {
            statement.executeUpdate("UPDATE "+db[0]+".language SET language = "+newLangauge+" WHERE uuid = ''"+uuid.toString()+"';'");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public String loadPlayer(UUID uuid){
        validateConnection();
        try {
            ResultSet result = statement.executeQuery("SELECT language FROM "+db[0]+".language WHERE uuid = '"+uuid.toString()+"';'");
            result.next();
            return result.getString(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public List<UUID> getRegisteredPlayers(){
        List<UUID> players = new ArrayList<>();
        try {
            validateConnection();
            ResultSet result = statement.executeQuery("SELECT uuid FROM "+db[0]+".language");
            while (result.next()){
                players.add(UUID.fromString(result.getString(1)));
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return players;
    }

}
