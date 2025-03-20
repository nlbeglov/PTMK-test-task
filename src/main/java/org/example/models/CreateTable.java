package org.example.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.models.ConnectionSQL.getConnection;

public class CreateTable {

    public static void createTableSQL() {

        try (Connection conn = getConnection();
        Statement stmt = conn.createStatement()) {
            System.out.println("Подключение к бд успешно!");
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "fio VARCHAR(50) NOT NULL," +
                    "birthday DATE NOT NULL," +
                    "sex ENUM('Male', 'Female') NOT NULL)";

            stmt.executeUpdate(createTableSQL);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
