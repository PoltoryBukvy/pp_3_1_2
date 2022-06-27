package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User save (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public User find(long id) {
        return userDao.getById(id);
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @PostConstruct
    public void init() {
        Role userRole = roleDao.getByName("user");
        if(userRole == null) {
            userRole = new Role("user");
            roleDao.save(userRole);
        }

        Role adminRole = roleDao.getByName("admin");
        if(adminRole == null) {
            adminRole = new Role("admin");
            roleDao.save(adminRole);
        }

        if(userDao.getByEmail("admin@mail.ru") == null) {
            User admin = new User("admin", "admin", "admin@mail.ru");
            admin.setPassword(passwordEncoder.encode("1111"));
            admin.setRoles(List.of(userRole, adminRole));
            userDao.save(admin);
        }

        if(userDao.getByEmail("user@mail.ru") == null) {
            User user = new User("user", "user", "user@mail.ru");
            user.setPassword(passwordEncoder.encode("2222"));
            user.setRoles(List.of(userRole));
            userDao.save(user);
        }

    }

}