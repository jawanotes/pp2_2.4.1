package jm.task.core.crud.dao;

import jm.task.core.crud.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jm.task.core.crud.util.Util.getSessionFactory;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory SESSION_FACTORY = getSessionFactory();
    private static final String TABLE_NAME = User.class.getSimpleName();
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String AGE_FIELD = "age";
    private static final String LASTNAME_FIELD = "lastname";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" +
                ID_FIELD + " BIGINT NOT NULL auto_increment, " +
                NAME_FIELD + " VARCHAR(10), " +
                LASTNAME_FIELD + " VARCHAR(10), " +
                AGE_FIELD + " TINYINT," +
                "PRIMARY KEY (id)) DEFAULT CHARSET=utf8mb4";

        executeQuery(queryString);
    }

    @Override
    public void dropUsersTable() {
        String queryString = "DROP TABLE IF EXISTS " + TABLE_NAME;

        executeQuery(queryString);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();

        user.setId(id);
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String queryString = "SELECT * FROM " + TABLE_NAME;
        List<User> userList;

        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            userList = session.createNativeQuery(queryString, User.class).list();
            transaction.commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String queryString = "TRUNCATE TABLE " + TABLE_NAME;

        executeQuery(queryString);
    }

    private void executeQuery(String queryString) {
        try(Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(queryString).executeUpdate();
            transaction.commit();
        }
    }
}
