package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnectionHibernate();
    private Session session;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, FirstName NVARCHAR(20), LastName NVARCHAR(20), Age TINYINT)";
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (session.get(User.class, id) != null) {
            session.remove(session.get(User.class, id));
        }
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> usersList = session.createQuery("from User").list();
        session.getTransaction().commit();
        for (User u: usersList) {
            System.out.println(u);
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
    }
}
