package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api")
public class RestApiController {

    private final UserService userService;

    public RestApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> list() {
        return userService.getAllUsers();
    }

    @PostMapping("/user")
    public User save(@ModelAttribute User user, @RequestParam("roless") String[] roless) {
        List<Role> roles = new ArrayList<>();
        for (String roleId : roless) {
            roles.add(new Role(Long.parseLong(roleId)));
        }
        user.setRoles(roles);
        return userService.save(user);
    }

    @GetMapping(value = "/user/{id}")
    public User show(@PathVariable(value = "id") long id, @RequestParam("delete") Optional<Boolean> isDelete) {
        return userService.find(id);
    }

    @PutMapping("/user/update")
    public User edit(@ModelAttribute User user, @RequestParam("roless") Optional<String[]> roless) {
        List<Role> roles = new ArrayList<>();
        if (roless.isPresent()) {
            for (String roleId : roless.get()) {
                roles.add(new Role(Long.parseLong(roleId)));
            }
        }
        user.setRoles(roles);
        return userService.update(user);
    }

    @DeleteMapping(value = "/user/{id}/delete")
    public User delete(@PathVariable(value = "id") long id) {
        return userService.delete(userService.find(id));
    }
}
