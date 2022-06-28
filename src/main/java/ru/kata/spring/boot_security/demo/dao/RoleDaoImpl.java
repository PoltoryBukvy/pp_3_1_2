package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

    @Override
    public Role getByName(String name) {
        return em.createQuery("SELECT r from Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name).getResultList().stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        em.persist(role);
        return role;
    }
}
