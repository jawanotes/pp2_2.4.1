package jm.task.core.crud.dao;

import javax.persistence.PersistenceContext;
import jm.task.core.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    @PersistenceContext
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
    @Transactional
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
    @Transactional
    public void dropUsersTable() {
        String queryString = "DROP TABLE IF EXISTS " + TABLE_NAME;

        executeQuery(queryString);
    }

    @Transactional
    @Override
    public User createUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        entityManager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        user = entityManager.find(User.class, user.getId());
        entityManager.remove(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        String queryString = "TRUNCATE TABLE " + TABLE_NAME;

        executeQuery(queryString);
    }

    private void executeQuery(String queryString) {
        entityManager.createNativeQuery(queryString).executeUpdate();
    }
}
