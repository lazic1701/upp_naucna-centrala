package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "_user")
public class User implements UserDetails {

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

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean active;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "_user_naucnaoblast",
            joinColumns = @JoinColumn(name = "naucnaoblast_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<NaucnaOblast> naucneOblastiUser = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "_user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "glavniUrednik", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Casopis> casopisiGlavniUrednik = new HashSet<>();

    @ManyToMany(mappedBy = "urednici")
    private Set<Casopis> casopisiUrednik = new HashSet<>();

    @ManyToMany(mappedBy = "recenzenti")
    private Set<Casopis> casopisiRecenzent = new HashSet<>();

    public User() {
    }

    public User(String city, String country, String title, String email, String username, String password, String firstname, String lastname, boolean active, Set<NaucnaOblast> naucneOblastiUser, List<Authority> authorities, Set<Casopis> casopisiGlavniUrednik, Set<Casopis> casopisiUrednik, Set<Casopis> casopisiRecenzent) {
        this.city = city;
        this.country = country;
        this.title = title;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.active = active;
        this.naucneOblastiUser = naucneOblastiUser;
        this.authorities = authorities;
        this.casopisiGlavniUrednik = casopisiGlavniUrednik;
        this.casopisiUrednik = casopisiUrednik;
        this.casopisiRecenzent = casopisiRecenzent;
    }



    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<Authority> getAuthorities() {
        return this.authorities;
    }


    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
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

    public Set<NaucnaOblast> getNaucneOblastiUser() {
        return naucneOblastiUser;
    }

    public void setNaucneOblastiUser(Set<NaucnaOblast> naucneOblastiUser) {
        this.naucneOblastiUser = naucneOblastiUser;
    }

    public Set<Casopis> getCasopisiGlavniUrednik() {
        return casopisiGlavniUrednik;
    }

    public void setCasopisiGlavniUrednik(Set<Casopis> casopisiGlavniUrednik) {
        this.casopisiGlavniUrednik = casopisiGlavniUrednik;
    }

    public Set<Casopis> getCasopisiUrednik() {
        return casopisiUrednik;
    }

    public void setCasopisiUrednik(Set<Casopis> casopisiUrednik) {
        this.casopisiUrednik = casopisiUrednik;
    }

    public Set<Casopis> getCasopisiRecenzent() {
        return casopisiRecenzent;
    }

    public void setCasopisiRecenzent(Set<Casopis> casopisiRecenzent) {
        this.casopisiRecenzent = casopisiRecenzent;
    }
}
