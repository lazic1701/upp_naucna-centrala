package org.milan.naucnacentrala.security.auth;

public class UserTokenState {


    private int id;
    private String role;
    private String accessToken;
    private Long expiresIn;

    public UserTokenState() {
        this.id = 0;
        this.role = "";
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(int id, String role, String accessToken, long expiresIn) {
        this.id = id;
        this.role = role;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}