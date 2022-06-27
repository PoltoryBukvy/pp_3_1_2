package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User getById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("Select u from User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public User update(User user) {
        em.merge(user);
        return user;
    }

    @Override
    @Transactional
    public User delete(User user) {
        em.remove(user);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return em.createQuery("SELECT u from User u WHERE u.email = :email", User.class)
                .setParameter("email", email).getResultList().stream()
                .findFirst()
                .orElse(null);
    }

}