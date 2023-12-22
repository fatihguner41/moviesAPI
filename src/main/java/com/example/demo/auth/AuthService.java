package com.example.demo.auth;

import com.example.demo.dto.user.RefreshTokenRequest;
import com.example.demo.dto.user.SignInRequest;
import com.example.demo.dto.user.SignInResponse;
import com.example.demo.dto.user.SignUpRequest;
import com.example.demo.services.JWTService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, JWTService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;

        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUp(SignUpRequest signUpRequest) {
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User user = new User(signUpRequest);
        userRepository.addUser(user.getUsername(), user.getPassword(), user.getEmail());
        return userRepository.findByUsername(user.getUsername()).orElseThrow();


    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("invalid username or password"));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new SignInResponse(token, refreshToken);
    }

    public SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            String token = jwtService.generateToken(user);
            String refreshToken = refreshTokenRequest.getToken();

            return new SignInResponse(token, refreshToken);

        }

        return null;
    }

}
