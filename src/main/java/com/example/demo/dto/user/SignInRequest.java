package com.example.demo.dto.user;

public class SignInRequest {
    private final String username;
    private final String password;


    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
