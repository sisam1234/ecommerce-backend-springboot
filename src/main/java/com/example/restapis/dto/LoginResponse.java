package com.example.restapis.dto;

public class LoginResponse {
private String token;
    private Long id;
    private String name;
    private String email;
     private long expiresIn;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }


}
