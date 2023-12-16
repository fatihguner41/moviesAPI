package com.example.demo.services;

import com.example.demo.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUserName(String token);

    boolean isTokenValid(String token,UserDetails userDetails);
    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);
}
