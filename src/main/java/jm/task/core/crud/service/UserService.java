package jm.task.core.crud.service;

import jm.task.core.crud.model.User;

import java.util.List;

public interface UserService {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);
    User createUser(User user);

    void removeUserById(long id);

    void removeUser(User user);

    List<User> getAllUsers();

    User getUser(long id);

    User updateUser(User user);

    void cleanUsersTable();
}
