package org.example.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSQL {
    public static java.sql.Connection getConnection() throws SQLException {
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            prop.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
