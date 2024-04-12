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

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
