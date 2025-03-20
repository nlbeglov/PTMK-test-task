package org.example.models;

import java.sql.*;

import static org.example.models.ConnectionSQL.getConnection;

public class FilterSimple {
    private static final String selectSQL = "SELECT fio, birthday, sex " +
            "FROM users WHERE sex = 'Male' AND fio LIKE 'F%'";

    public static void startSimpleFilter() {


        try (Connection conn = getConnection()) {
            System.out.println("Подключение к бд успешно!");
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);

            long startTime = System.currentTimeMillis();
            ResultSet rs = pstmt.executeQuery();
            long endTime = System.currentTimeMillis();

            System.out.println("FIO\tBirthday\tSex");
            while (rs.next()) {
                System.out.println(rs.getString("fio") + "\t" + rs.getString("birthday") + "\t" + rs.getString("sex"));
            }


            double elapsedTime = (endTime - startTime);
            System.out.println("\nВремя выполнения запроса: " + elapsedTime + " мс");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}