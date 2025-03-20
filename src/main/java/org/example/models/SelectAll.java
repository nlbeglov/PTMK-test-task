package org.example.models;

import java.sql.*;

import static org.example.models.ConnectionSQL.getConnection;

public class SelectAll {

    public static void selectAge() {

        try (Connection conn = getConnection()) {
            System.out.println("Подключение к бд успешно!");
            String selectSQL = "SELECT DISTINCT fio, birthday, sex, TIMESTAMPDIFF(YEAR, birthday, CURDATE()) AS age " +
                    "FROM users " +
                    "ORDER BY fio;";
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("%-20s %-12s %-8s %s%n", "ФИО", "Дата рождения", "Пол", "Возраст");
            System.out.println();

            while (rs.next()) {
                String fio = rs.getString("fio");
                Date birthday = rs.getDate("birthday");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");

                System.out.printf("%-20s %-12s %-8s %d%n", fio, birthday, sex, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
