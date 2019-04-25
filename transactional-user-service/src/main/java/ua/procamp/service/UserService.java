package ua.procamp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.dao.UserDao;
import ua.procamp.model.jpa.Role;
import ua.procamp.model.jpa.RoleType;
import ua.procamp.model.jpa.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class proovides {@link User} related service logic.
 * <p>
 * todo: 1. Configure {@link UserService} bean as spring service
 * todo: 2. Inject {@link UserDao} using constructor-based injection
 * todo: 3. Enable transaction management on class level
 * todo: 4. Configure {@link UserService#getAll()} as read-only method
 * todo: 4. Configure {@link UserService#getAllAdmins()} as read-only method
 */
@Service
@Transactional
public class UserService {

    private UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        Objects.requireNonNull(user);
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getAllAdmins() {
        return userDao.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .map(Role::getRoleType)
                        .anyMatch(type -> type.equals(RoleType.ADMIN))
                ).collect(Collectors.toList());
    }

    public void addRole(Long userId, RoleType roleType) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(roleType);
        User user = userDao.findById(userId);
        if (Objects.isNull(user)){
            throw new IllegalArgumentException(String.format("No user with id %d", userId));
        }
        user.getRoles().add(Role.valueOf(roleType));
        userDao.save(user);
    }
}
