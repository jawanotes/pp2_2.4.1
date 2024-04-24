package jm.task.core.crud.service;

import jm.task.core.crud.dao.UserDao;
import jm.task.core.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }
    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.createUser(name, lastName, age);
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    public void removeUser(User user) {
        userDao.removeUser(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }
    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
