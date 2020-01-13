package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column
    private String title;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Enums.UserRole userRole;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean active;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "autor_naucnaoblast",
            joinColumns = @JoinColumn(name = "naucnaoblast_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<NaucnaOblast> naucneOblastiAutor = new HashSet<>();

    public User() {
    }

    public User(String city, String country, String title, String email, String username, String password, String firstname, String lastname, Enums.UserRole userRole, boolean active, Set<NaucnaOblast> naucneOblastiAutor) {
        this.city = city;
        this.country = country;
        this.title = title;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userRole = userRole;
        this.active = active;
        this.naucneOblastiAutor = naucneOblastiAutor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Enums.UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(Enums.UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<NaucnaOblast> getNaucneOblastiAutor() {
        return naucneOblastiAutor;
    }

    public void setNaucneOblastiAutor(Set<NaucnaOblast> naucneOblastiAutor) {
        this.naucneOblastiAutor = naucneOblastiAutor;
    }
}
