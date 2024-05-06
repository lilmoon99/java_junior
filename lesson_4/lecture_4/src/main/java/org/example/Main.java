package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "moon", "");) {
//            prepareTable(connection);
            run(connection);
        }
    }

    private static void prepareTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE users(
                id BIGINT,
                login VARCHAR(256),
                active BOOLEAN
                )
                """);
        statement.execute("""
                INSERT INTO users(id,login,active) VALUES
                (1,'moon',true),
                (2,'vienna',true),
                (3,'jarvis',false)
                """);
    }

    private static void run(Connection connection) {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
//            hibernateCRUD(sessionFactory);
            try (Session session = sessionFactory.openSession()) {
                User user = new User();
                user.setId(123L);
                user.setLogin("pet_owner");
                user.setActive(Boolean.TRUE);

                Pet pet = new Pet();
                pet.setId(1L);
                pet.setName("pet");
                pet.setOwner(user);

                Transaction tx = session.beginTransaction();
                session.persist(user);
                session.persist(pet);
                tx.commit();
            }
            try (Session session = sessionFactory.openSession()){
                Pet pet = session.find(Pet.class, 1L);
                System.out.println(pet);
            }

            try (Session session = sessionFactory.openSession()) {
//                User singleResult = session.createQuery("SELECT u FROM User u WHERE id = 11", User.class).getSingleResult();
//                System.out.println(singleResult);
                Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.login in('moon','vienna')", User.class);
                query.stream().forEach(System.out::println);
            }
        }
    }

    private static void hibernateCRUD(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, 1L); //SELECT
            System.out.println(user);
        }
        User user = new User();
        user.setId(4L);
        user.setLogin("murzik");
        user.setActive(Boolean.TRUE);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();

        }
        try (Session session = sessionFactory.openSession()) {
            User savedUser = session.find(User.class, 4L); //SELECT
            System.out.println(savedUser);
        }
    }
}