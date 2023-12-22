package com.example.demo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class UpdateAuthenticatedUserRequest {
    private String username;
    private String email;
    private String password;
}
