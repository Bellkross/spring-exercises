package ua.procamp.dao.impl;

import org.springframework.transaction.annotation.Transactional;
import ua.procamp.dao.CustomUserRepository;
import ua.procamp.model.jpa.Role;
import ua.procamp.model.jpa.RoleType;
import ua.procamp.model.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void addRoleToAllUsers(final RoleType roleType) {
        Objects.requireNonNull(roleType);
        entityManager.createQuery("select u from User u", User.class).getResultList()
                .forEach(u -> {
                            if (u.getRoles().stream().map(Role::getRoleType).noneMatch(rt -> rt.equals(roleType))) {
                                Role r = Role.valueOf(roleType);
                                r.setUser(u);
                                u.addRole(r);
                                entityManager.persist(r);
                            }
                        }
                );
    }
}
