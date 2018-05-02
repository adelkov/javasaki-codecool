package com.codecool.shop.dao.implementation;

import java.sql.*;

public class DBConnector {
    private static final String DATABASE = System.getenv("DATABASE");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection()
    {
        try {
            return DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}
