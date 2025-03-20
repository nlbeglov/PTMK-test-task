package org.example.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.example.models.ConnectionSQL.getConnection;

public class DataGenerator {
    private static final String insertSQL = "INSERT INTO users (fio, birthday, sex) VALUES (?,?,?)";
    private static List<String> firstNames = new ArrayList<>();
    private static List<String> lastNames = new ArrayList<>();
    private static List<String> middleNames = new ArrayList<>();

    private static void loadData(String filename, List<String> list) {
        try {
            Path path = Paths.get(filename);
            list.addAll(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomFIO() {
        Random rand = new Random();
        String first = firstNames.get(rand.nextInt(firstNames.size()));
        String last = middleNames.get(rand.nextInt(middleNames.size()));
        String middle = middleNames.get(rand.nextInt(middleNames.size()));
        return first + " " + last + " " + middle;
    }

    private static LocalDate getRandomBirthday() {
        int year = 1920 + new Random().nextInt(100);
        int month = 1 + new Random().nextInt(12);
        int day = 1 + new Random().nextInt(28);
        return LocalDate.of(year, month, day);
    }

    public static void generateData() {
        loadData("src/main/resources/last_names.csv", lastNames);
        loadData("src/main/resources/first_names.csv", firstNames);
        loadData("src/main/resources/middle_names.csv", middleNames);
        int totalRows = 1_000_000;
        int batchSize = 1000;
        Random rand = new Random();

        try (Connection conn = getConnection()){
            System.out.println("Подключение к бд успешно!");
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            conn.setAutoCommit(false);
            int insertedRows = 0;

            for (int i = 0; i < totalRows; i++) {
                String fio = getRandomFIO();
                LocalDate birthday = getRandomBirthday();
                String sex = (rand.nextBoolean() ? "Male" : "Female");

                pstmt.setString(1, fio);
                pstmt.setDate(2, Date.valueOf(birthday));
                pstmt.setString(3, sex);
                pstmt.addBatch();

                if (++insertedRows % batchSize == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    if (insertedRows % 100_000 == 0)
                        System.out.println("Процесс добавления: " + insertedRows + " строк добавлено...");
                }
            }
            pstmt.executeBatch();
            conn.commit();
            System.out.println("Успешно добавлено " + insertedRows + " строк");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
