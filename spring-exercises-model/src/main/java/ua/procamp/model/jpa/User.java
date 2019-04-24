package ua.procamp.model.jpa;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();

    public void setAddress(Address address) {
        if (address != null) {
            address.setUser(this);
        } else if (this.address != null) {
            this.address.setUser(null);
        }
        this.address = address;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.setUser(this);
    }

    public void addRoles(List<Role> roles) {
        this.roles.addAll(roles);
        roles.forEach(role -> role.setUser(this));
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.setUser(null);
    }
}
