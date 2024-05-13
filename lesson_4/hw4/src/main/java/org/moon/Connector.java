package org.moon;

import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.cfg.Configuration;

public class Connector {




    public Connector() {
    }

    public static SessionFactory getSessionFactory(){
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test","moon","");
    }
}
