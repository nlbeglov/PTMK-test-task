package org.example;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.time.LocalDate;
import java.time.Period;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {



        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            System.out.println("Подключение к бд успешно!");

            int param = Integer.parseInt(args[0]);

            switch (param) {
                case 1: {
                    String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "fio VARCHAR(50) NOT NULL," +
                            "birthday DATE NOT NULL," +
                            "sex ENUM('Male', 'Female') NOT NULL)";

                    stmt.executeUpdate(createTableSQL);
                    System.out.println("Таблица создана!");
                    break;
                }
                case 2: {
                    String insertSQL = "INSERT INTO users (fio, birthday, sex) VALUES (?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                    pstmt.setString(1, args[1]);
                    pstmt.setString(2, args[2]);
                    pstmt.setString(3, args[3]);

                    pstmt.executeUpdate();
                    break;
                }
                case 3: {
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
                    break;
                }
                case 4: {

                }
            }

        } catch (SQLException e) {
            System.out.println("Ошибка подключения к бд:");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
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
