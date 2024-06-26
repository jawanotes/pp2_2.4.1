package jm.task.core.crud.util;

import jm.task.core.crud.model.User;
import jm.task.core.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("dev")
@Component
public class DatabaseInitializer {
    UserService userService;

    @Autowired
    public DatabaseInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        if(userService.getAllUsers().isEmpty()) {
            userService.createUser(new User("Andrey", "Andreev", (byte) 10));
            userService.createUser(new User("Boris", "Borisov", (byte) 20));
            userService.createUser(new User("Viktor", "Viktorov", (byte) 30));
        }
    }
}
