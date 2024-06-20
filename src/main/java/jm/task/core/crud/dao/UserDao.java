package jm.task.core.crud.dao;

import jm.task.core.crud.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    //@Transactional
    User createUser(String name, String lastName, byte age);
    User createUser(User user);
    User updateUser(User user);
    User getUser(long id);

    void removeUserById(long id);
    void removeUser(User user);

    List<User> getAllUsers();

    void cleanUsersTable();
}
