package com.example.demo.user;
import com.example.demo.dto.AuthenticatedUserRequest;
import com.example.demo.dto.UpdateAuthenticatedUserRequest;
import com.example.demo.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        try {
            User user=userService.getUserByUsername(username);
            UserDTO userDTO = new UserDTO(user.getUsername(),user.getEmail(),user.getRole());
            return ResponseEntity.ok(userDTO);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usernameNotFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(@RequestHeader("Authorization") String bearerToken){
        try {
            String token=extractBearerToken(bearerToken);
            return ResponseEntity.ok(userService.getAuthenticatedUser(token));
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usernameNotFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PostMapping("/me/update")
    public ResponseEntity<?> updateAuthenticatedUser(@RequestHeader("Authorization") String bearerToken,
                                                     @RequestBody UpdateAuthenticatedUserRequest updateAuthenticatedUserRequest){
        try {
            String token=extractBearerToken(bearerToken);
            return ResponseEntity.ok(userService.updateAuthenticatedUser(token,updateAuthenticatedUserRequest));
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usernameNotFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
    @DeleteMapping("/me/delete")
    public ResponseEntity<String> deleteAuthenticatedUser(@RequestHeader("Authorization") String bearerToken){
        try {
            String token=extractBearerToken(bearerToken);
            return ResponseEntity.ok(userService.deleteAuthenticatedUser(token));
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usernameNotFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    private String extractBearerToken(String authorizationHeader) {
        // Authorization header'ındaki Bearer token'ını çıkart
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
