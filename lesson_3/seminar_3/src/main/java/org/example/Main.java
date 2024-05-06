package org.example;


import java.sql.*;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "root", "root")) {
            acceptConnection(connection);
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к БД: " + e.getMessage());
        }
    }

    static void acceptConnection(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE person(
                    id BIGINT,
                    name VARCHAR(256),
                    age SMALLINT
                    )
                    """);
        }

        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate("""
                    INSERT INTO person(id,name,age) VALUES
                    (1,'Igor',25),
                    (2,'John',36),
                    (3,'Peter',47)
                    """);
            System.out.println("Количество вставленных строк: " + count);
        }

        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate("""
                    UPDATE person
                    SET age = -1
                    WHERE id > 1
                    """);
            System.out.println("Количество обновленных строк: " + count);
        }


        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT id,name,age
                    FROM person
                    """);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("Прочитана строка:" + String.format("%s, %s, %s", id, name, age));
            }
        }

        removePersonById(connection,"1");


    }

    static void removePersonById(Connection connection, String idParameter) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int deletedRowsCount = statement.executeUpdate("DELETE FROM person WHERE id = " + idParameter);
            System.out.println("Удалено строк: " + deletedRowsCount);
        }
        System.out.println("After delete:");
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT id,name,age
                    FROM person
                    """);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("Прочитана строка:" + String.format("%s, %s, %s", id, name, age));
            }
        }
    }
}