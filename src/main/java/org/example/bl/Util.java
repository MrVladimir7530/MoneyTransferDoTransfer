package org.example.bl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Util {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Payments";
    private static final String DB_USERNAME = "AccountTest";
    private static final String DB_PASSWORD = "1234567890";

    public Connection getConnection() throws SQLException{
        Connection connection = null;
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Connection OK");
            } else {
                System.out.println("Connection Not");
            }
        return connection;
    }
}
