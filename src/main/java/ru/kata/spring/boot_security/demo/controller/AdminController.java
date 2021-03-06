package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(path = "admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/user")
    public String form(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/user")
    public String save(@ModelAttribute User user, Model model) {
        model.addAttribute("user", userService.save(user));
        return "form";
    }

    @GetMapping(value = "/user/{id}")
    public String show(@PathVariable(value = "id") long id, Model model) {
        User user = userService.find(id);
        if (user == null) {
            return "404";
        }
        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping("/user/{id}")
    public String edit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", userService.save(user));
        return "form";
    }

    @DeleteMapping(value = "/user/{id}/delete")
    public String delete(@PathVariable(value = "id") long id, Model model) {
        User user = userService.find(id);
        if (user == null) {
            return "404";
        }
        userService.delete(user);
        return list(model);
    }
}
