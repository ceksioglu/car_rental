package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    //Singleton Design Pattern

    private static Database instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/carrental";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "root";

    private Database() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        } catch (SQLException e) {
            e.getMessage();
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }
}