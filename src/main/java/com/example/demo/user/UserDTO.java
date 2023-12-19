package com.example.demo.user;

public class UserDTO {
    private String username;
    private String email;

    private UserRole role;

    public UserDTO(String username, String email, UserRole role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }


    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
