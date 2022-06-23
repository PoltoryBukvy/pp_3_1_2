package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role getById(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("Select u from Role u", Role.class).getResultList();
    }
}
