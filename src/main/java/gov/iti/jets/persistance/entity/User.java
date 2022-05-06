package gov.iti.jets.persistance.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.persistance.entity.*;
import gov.iti.jets.persistance.entity.enums.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL)
    List<Order> orders;

    public User() {

    }

    public User(String firstName, String lastName, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.orders = new ArrayList<>();
    }
    public User(int id, String firstName, String lastName, String email, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.orders = new ArrayList<>();
    }

}
