package com.codecool.shop.dao.implementation;

import java.sql.*;

public class DBConnector {
    private static DBConnector instance = null;
    private static final String DATABASE = System.getenv("DATABASE");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    protected DBConnector() {
    }
    public static DBConnector getInstance() {
        if(instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public void executeTestQuery(String query, String... params) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ){
            int idx = 1;
            for (String param : params)
            {
                statement.setString(idx++, param);
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectQuery(String query,String... params){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ){
            int idx = 1;
            for (String param : params)
            {
                statement.setString(idx++, param);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement setStatement(String query, String[] params){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ){
            int idx = 1;
            for (String param : params)
            {
                statement.setString(idx++, param);
            }
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
