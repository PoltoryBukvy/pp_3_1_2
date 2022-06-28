package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleDao {
    public Role getById(long l);

    public List<Role> getAllRoles();

    Role getByName(String name);

    @Transactional
    Role save(Role role);
}
