package jm.task.core.crud.dao;

import jm.task.core.crud.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
