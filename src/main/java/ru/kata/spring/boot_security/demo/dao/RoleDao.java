package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleDao {
    public Role getById(long l);

    public Role getByName(String name);

    public Role save(Role userRole);
}
