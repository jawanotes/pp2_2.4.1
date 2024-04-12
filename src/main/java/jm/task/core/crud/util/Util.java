package jm.task.core.crud.util;

import jm.task.core.crud.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String USER = "hkl";
    private static final String PASS = "secret";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp211";

    private Util() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static SessionFactory getSessionFactory() {
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", DB_URL);
        prop.setProperty("hibernate.connection.username", USER);
        prop.setProperty("hibernate.connection.password", PASS);

        return new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
    }
}
