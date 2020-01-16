package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;

public class UserDTO {

    private int id;
    private String city;
    private String country;
    private String title;
    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public UserDTO() {
    }

    public UserDTO(int id, String city, String country, String title, String email, String username, String password, String firstname, String lastname) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.title = title;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static UserDTO formDto(User user) {
        if (user == null) {
            return new UserDTO();
        } else {
            return new UserDTO(user.getId(), user.getCity(), user.getCountry(), user.getTitle(), user.getEmail(),
                    user.getUsername(), "", user.getFirstname(), user.getLastname());
        }
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
}
