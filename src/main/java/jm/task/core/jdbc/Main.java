package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    static final String USER_ADDED_STRING = "User с именем – %s добавлен в базу данных\n";
    public static void main(String[] args) {
        User man;

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        man = new User("Ivan", "Ivanov", (byte) 11);
        userService.saveUser(man.getName(), man.getLastName(), man.getAge());
        System.out.printf(USER_ADDED_STRING, man.getName());

        man = new User("Petr", "Petrov", (byte) 22);
        userService.saveUser(man.getName(), man.getLastName(), man.getAge());
        System.out.printf(USER_ADDED_STRING, man.getName());

        man = new User("Sidor", "Sidorov", (byte) 33);
        userService.saveUser(man.getName(), man.getLastName(), man.getAge());
        System.out.printf(USER_ADDED_STRING, man.getName());

        man = new User("Незнайка", "Незнанский", (byte) 44);
        userService.saveUser(man.getName(), man.getLastName(), man.getAge());
        System.out.printf(USER_ADDED_STRING, man.getName());

        List<User> userList = userService.getAllUsers();

        System.out.println(userList);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
