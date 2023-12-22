package com.example.demo.auth;

import com.example.demo.dto.user.RefreshTokenRequest;
import com.example.demo.dto.user.SignInRequest;
import com.example.demo.dto.user.SignInResponse;
import com.example.demo.dto.user.SignUpRequest;
import com.example.demo.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){

        return ResponseEntity.ok( authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest){

        return ResponseEntity.ok( authService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){

        try {
            return ResponseEntity.ok( authService.refreshToken(refreshTokenRequest));
        } catch (UsernameNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
