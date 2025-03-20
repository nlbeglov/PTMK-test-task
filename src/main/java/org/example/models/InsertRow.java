package org.example.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.models.ConnectionSQL.getConnection;

public class InsertRow {

    public static void insertRowSQL(String[] args) {

        try (Connection conn = getConnection()) {
            System.out.println("Подключение к бд успешно!");
            String insertSQL = "INSERT INTO users (fio, birthday, sex) VALUES (?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, args[1]);
            pstmt.setString(2, args[2]);
            pstmt.setString(3, args[3]);

            pstmt.executeUpdate();
            System.out.println("Строка добавлена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
