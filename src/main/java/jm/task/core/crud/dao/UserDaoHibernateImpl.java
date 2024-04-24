package jm.task.core.crud.dao;

import jm.task.core.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    private final EntityManager entityManager;
    private static final String TABLE_NAME = User.class.getSimpleName();
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String AGE_FIELD = "age";
    private static final String LASTNAME_FIELD = "lastname";

    @Autowired
    public UserDaoHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
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
    public User createUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        entityManager.persist(user);
        /*try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }*/
        return user;
    }

    @Override
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User getUser(long id) {
        User user = new User();

        user.setId(id);
        return entityManager.find(User.class, user);
        //return null;
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();

        user.setId(id);
        entityManager.remove(user);
        /*try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }*/
    }

    @Override
    public void removeUser(User user) {
        entityManager.remove(user);
    }

    @Override
    public List<User> getAllUsers() {
        /*String queryString = "SELECT * FROM " + TABLE_NAME;
        List<User> userList;*/

        return entityManager.createQuery("FROM User", User.class).getResultList();
        /*try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            userList = session.createNativeQuery(queryString, User.class).list();
            transaction.commit();
        }*/
        //return userList;
    }

    @Override
    public void cleanUsersTable() {
        String queryString = "TRUNCATE TABLE " + TABLE_NAME;

        executeQuery(queryString);
    }

    private void executeQuery(String queryString) {
        entityManager.createNativeQuery(queryString).executeUpdate();
        /*try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(queryString).executeUpdate();
            transaction.commit();
        }*/
    }
}
