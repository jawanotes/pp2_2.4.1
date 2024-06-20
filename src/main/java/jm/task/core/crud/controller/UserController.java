package jm.task.core.crud.controller;

import jm.task.core.crud.model.User;
import jm.task.core.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;
    private static final String REDIRECT_USERS = "redirect:/users";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(ModelMap model) {
        model.addAttribute("userlist", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/addnew")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return REDIRECT_USERS;
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return REDIRECT_USERS;
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUserById(id);
        return REDIRECT_USERS;
    }

    @GetMapping("/")
    public String start() {
        return REDIRECT_USERS;
    }
}
