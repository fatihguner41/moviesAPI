package com.example.demo.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticatedUserRequest {
    private String token;
}
