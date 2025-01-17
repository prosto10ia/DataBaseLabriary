package org.example.libriary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USER = "a1111";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (Exception e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeQuery(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public PreparedStatement prepareStatement(String query) {
        try {
            if (connection != null) {
                return connection.prepareStatement(query);
            } else {
                throw new IllegalStateException("Connection to the database is not established.");
            }
        } catch (Exception e) {
            System.out.println("Error preparing statement: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
