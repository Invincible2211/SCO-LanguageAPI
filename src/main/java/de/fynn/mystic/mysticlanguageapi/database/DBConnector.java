package de.fynn.mystic.mysticlanguageapi.database;

import de.fynn.mystic.mysticlanguageapi.file.CFGLoader;

import java.sql.*;

public class DBConnector {

    private Connection connection;
    private Statement statement;
    private String[] db = CFGLoader.getDBData();

    {
        try {
            connect();
            statement.execute("CREATE SCHEMA IF NOT EXISTS "+db[0]+";");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void connect() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://"+db[1]+":"+db[2],db[3],db[4]);
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

    public void executeSQL(String sql){
        try {
            statement.execute(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ResultSet executeQuerry(String sql){
        try {
            return statement.executeQuery(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void update(String sql){
        try {
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
