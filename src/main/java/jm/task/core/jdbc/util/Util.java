package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/forusers";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "101192";

    private static final SessionFactory sessionFactory = new Configuration()
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    public static Connection getConnection () {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Соединение с базой данных выполнено");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка соединения с базой данных");
        }
        return connection;
    }

    public static SessionFactory getConnectionHibernate() {
        return sessionFactory;
    }
}
