package ua.procamp;


import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import ua.procamp.model.Account;
import ua.procamp.model.Gender;
import ua.procamp.model.jpa.Address;
import ua.procamp.model.jpa.Role;
import ua.procamp.model.jpa.RoleType;
import ua.procamp.model.jpa.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TestDataGenerator {

    public Account generateAccount(){
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        Random random = new Random();

        Account fakeAccount = new Account();
        fakeAccount.setFirstName(person.getFirstName());
        fakeAccount.setLastName(person.getLastName());
        fakeAccount.setEmail(person.getEmail());
        fakeAccount.setBirthday(LocalDate.of(
                person.getDateOfBirth().getYear(),
                person.getDateOfBirth().getMonthOfYear(),
                person.getDateOfBirth().getDayOfMonth()));
        fakeAccount.setGender(Gender.valueOf(person.getSex().name()));
        fakeAccount.setBalance(BigDecimal.valueOf(random.nextInt(200_000)));
        fakeAccount.setCreationTime(LocalDateTime.now());

        return fakeAccount;
    }

    private List<Role> generateRoleList() {
        Random random = new Random();
        Predicate<RoleType> randomPredicate = i -> random.nextBoolean();

        return Stream.of(RoleType.values())
                .filter(randomPredicate)
                .map(Role::valueOf)
                .collect(toList());
    }

    public User generateUser(RoleType... roles) {
        User user = generateUserWithoutRoles();
        Stream.of(roles)
                .map(Role::valueOf)
                .forEach(user::addRole);

        return user;
    }

    public User generateUserWithoutRoles() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();

        User user = new User();
        user.setFirstName(person.getFirstName());
        user.setLastName(person.getLastName());
        user.setEmail(person.getEmail());
        user.setBirthday(LocalDate.of(
                person.getDateOfBirth().getYear(),
                person.getDateOfBirth().getMonthOfYear(),
                person.getDateOfBirth().getDayOfMonth()));
        user.setCreationDate(LocalDate.now());

        Address address = generateAddress();
        user.setAddress(address);

        return user;
    }


    public User generateUser() {
        User user = generateUserWithoutRoles();
        user.addRoles(generateRoleList());

        return user;
    }

    private Address generateAddress() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();

        Address address = new Address();
        address.setCity(person.getAddress().getCity());
        address.setStreet(person.getAddress().getStreet());
        address.setStreetNumber(person.getAddress().getStreetNumber());
        address.setApartmentNumber(person.getAddress().getApartmentNumber());
        address.setCreationDate(LocalDateTime.now());
        address.setZipCode(person.getAddress().getPostalCode());

        return address;
    }

}