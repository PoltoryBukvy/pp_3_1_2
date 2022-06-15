package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


public interface UserDao {

        public User save(User user);

        public User getById(long id);

        public List<User> getAll();

        public User update(User user);

        public User delete(User user);

        public User getByEmail(String username);
}

