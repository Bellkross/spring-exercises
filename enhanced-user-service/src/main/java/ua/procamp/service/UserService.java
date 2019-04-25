package ua.procamp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.dao.UserRepository;
import ua.procamp.exception.EntityNotFoundException;
import ua.procamp.model.jpa.RoleType;
import ua.procamp.model.jpa.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class provides service logic for {@link User}.
 * <p>
 * todo: 0. PLEASE NOTE, THAT SOME REQUIRED STEPS ARE OMITTED IN THE TODO LIST AND YOU HAVE TO DO IT ON YOUR OWN
 * <p>
 * todo: 1. Implement {@link UserService#findByCity(String)} using {@link UserRepository}, make method read only
 * todo: 2. Implement {@link UserService#getByEmail(String)} using {@link UserRepository}, make method read only
 * todo: 3. In case user is not found by email, throw {@link EntityNotFoundException} with message "Cannot find user by email ${email}"
 * todo: 4. Implement {@link UserService#addRoleToAllUsers(RoleType)} using {@link UserRepository}
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findByCity(String city) {
        Objects.requireNonNull(city);
        return userRepository.findAllByAddress_City(city);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        Objects.requireNonNull(email);
        return Optional.ofNullable(userRepository.findUserAddressAndRolesByEmail(email))
                .orElseThrow((() ->
                                new EntityNotFoundException(String.format("Cannot find user by email %s", email))
                        )
                );
    }

    @Transactional
    public void addRoleToAllUsers(RoleType roleType) {
        Objects.requireNonNull(roleType);
        userRepository.addRoleToAllUsers(roleType);
    }
}
