package de.fynn.mystic.mysticlanguageapi.database;

import java.sql.ResultSet;
import java.util.UUID;

public class DBManager {

    private DBConnector connector = new DBConnector();

    public void insertPlayer(UUID uuid, String language){
        connector.executeSQL("");
    }

    public void updatePlayer(UUID uuid, String newLangauge){
        connector.update("");
    }

    public String loadPlayer(UUID uuid){
        ResultSet result = connector.executeQuerry("");
        return "";
    }

}
