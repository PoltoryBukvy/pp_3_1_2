package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User save(User user);

    public User find(long id);

    public List<User> getAllUsers();

    public void delete(User user);

    public User getByEmail(String email);

    public User update(User user);

}
