package org.example;

import java.sql.*;

//Повторить все, что было на семинаре на таблице Student с полями
//1. id - bigint
//2. first_name - varchar(256)
//3. second_name - varchar(256)
//4. group - varchar(128)
//
//Написать запросы:
//1. Создать таблицу
//2. Наполнить таблицу данными (insert)
//3. Поиск всех студентов
//4. Поиск всех студентов по имени группы
//
//Доп. задания:
//1. ** Создать таблицу group(id, name); в таблице student сделать внешний ключ на group
//2. ** Все идентификаторы превратить в UUID
//
//Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testbd", "root", "root");
        initDataBase(connection);
        getGroupNames(connection);
        getAllStudents(connection);
        getStudentsByGroupName(connection, "Java");
        getNormalizedStudents(connection);
    }

    static void initDataBase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS groups(
                        group_id BIGINT,
                        name VARCHAR(128),
                        CONSTRAINT groups_id_pk PRIMARY KEY(group_id)
                        )
                    """);
            statement.execute("""
                    INSERT INTO groups(group_id,name) VALUES
                    (1,'Python'),
                    (2,'Data Science'),
                    (3,'C#'),
                    (4,'Java Script'),
                    (5,'Java')
                                     
                    """);
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS students(
                    `student_id` BIGINT NOT NULL,
                    `first_name` VARCHAR (256) NOT NULL,
                    `second_name` VARCHAR (256) NOT NULL,
                    `group_id` BIGINT NOT NULL,
                    FOREIGN KEY (group_id) REFERENCES groups(group_id),
                    CONSTRAINT students_pk PRIMARY KEY(student_id)
                    )
                    """);
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    INSERT INTO students(student_id,first_name,second_name,group_id) VALUES
                    (1,'Ivan','Ivanov',1),
                    (2,'Maxim','Maximov',2),
                    (3,'Andrey','Andreev',3),
                    (4,'Alexey','Alexeev',4),
                    (5,'Sergey','Pankov',5)
                    """);
        }
        System.out.println("---------------------------------------------------");
    }

    static void getAllStudents(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT student_id,first_name,second_name,group_id
                    FROM students
                    """);
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String fName = resultSet.getString("first_name");
                String sName = resultSet.getString("second_name");
                String group = resultSet.getString("group_id");
                System.out.printf("Id:%s, First name: %s, Second name: %s, Group ID: %s.%n", id, fName, sName, group);
            }
        }
        System.out.println("---------------------------------------------------");
    }

    static void getStudentsByGroupName(Connection connection, String groupName) throws SQLException {
        String SQL_QUERY = """
                SELECT s.student_id,s.first_name,s.second_name,g.name
                FROM students s
                JOIN groups g
                ON s.group_id = g.group_id
                WHERE g.name = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.setString(1, groupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String fName = resultSet.getString("first_name");
                String sName = resultSet.getString("second_name");
                String group = resultSet.getString("name");
                System.out.printf("Id:%s, First name: %s, Second name: %s, Group: %s.%n", id, fName, sName, group);
            }
        }
        System.out.println("---------------------------------------------------");
    }

    static void getNormalizedStudents(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("""
                SELECT s.student_id,s.first_name,s.second_name,g.name
                FROM students s
                JOIN groups g
                ON s.group_id = g.group_id
                """);
        while (resultSet.next()) {
            int id = resultSet.getInt("student_id");
            String fName = resultSet.getString("first_name");
            String sName = resultSet.getString("second_name");
            String group = resultSet.getString("name");
            System.out.printf("Id:%s, First name: %s, Second name: %s, Group: %s.%n", id, fName, sName, group);
        }
    }

    static void getGroupNames(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM groups");
        while (resultSet.next()) {
            int id = resultSet.getInt("group_id");
            String name = resultSet.getString("name");
            System.out.printf("GROUP ID: %d, GROUP NAME: %s%n",id,name);
        }
        System.out.println("---------------------------------------------------");
    }
}

