package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

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
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> save(@ModelAttribute User user, @RequestParam("roless") Optional<String[]> roles) {
        return new ResponseEntity<>(userService.save(user, roles), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> show(@PathVariable(value = "id") long id, @RequestParam("delete") Optional<Boolean> isDelete) {
        return new ResponseEntity<>(userService.find(id), HttpStatus.OK);
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> edit(@ModelAttribute User user, @RequestParam("roless") Optional<String[]> roles) {
        return new ResponseEntity<>(userService.update(user, roles), HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}/delete")
    public ResponseEntity<User> delete(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(userService.delete(userService.find(id)), HttpStatus.OK);
    }
}
