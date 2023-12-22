package com.example.demo.user;
import com.example.demo.dto.user.AuthenticatedUserResponse;
import com.example.demo.dto.user.UpdateAuthenticatedUserRequest;
import com.example.demo.dto.user.UpdateAuthenticatedUserResponse;
import com.example.demo.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    @Autowired
    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;

        this.jwtService = jwtService;
    }

    @Bean
    public PasswordEncoder passwordEncoder2(){
        return new BCryptPasswordEncoder();

    }

    public List<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<UserDTO> usersDTO= new ArrayList<UserDTO>() ;
        for(User user : users){
            usersDTO.add(new UserDTO(user.getUsername(),user.getEmail(),user.getRole()));
        }
        return usersDTO;
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User with this username can not be found."));
            }
        };
    }

    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User with this username can not be found."));
    }


    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public AuthenticatedUserResponse getAuthenticatedUser(String token) {
        String username = jwtService.extractUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if(jwtService.isTokenValid(token, user)){
            return new AuthenticatedUserResponse(user.getUsername(),user.getEmail());
        }
        return null;

    }

    public UpdateAuthenticatedUserResponse updateAuthenticatedUser(String token, UpdateAuthenticatedUserRequest uReq){
        String username = jwtService.extractUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if(jwtService.isTokenValid(token, user)){
            // Güncelleme isteğini kullanarak alanları kontrol et ve güncelle
            if (uReq.getUsername() != null) {
                user.setUsername(uReq.getUsername());
            }

            if (uReq.getEmail() != null) {
                user.setEmail(uReq.getEmail());
            }

            if (uReq.getPassword() != null) {
                user.setPassword(passwordEncoder2().encode(uReq.getPassword()));
            }

            // Kullanıcıyı kaydet
            userRepository.save(user);

            String newToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);
            UpdateAuthenticatedUserResponse uRes= new UpdateAuthenticatedUserResponse(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    newToken,
                    newRefreshToken

            );
            return uRes;
        }

        return null;
    }

    public String deleteAuthenticatedUser(String token) {
        String username = jwtService.extractUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if(jwtService.isTokenValid(token, user)){
            userRepository.deleteById(user.getUser_id());
            return "User is deleted";
        }
        return "Delete failed.";
    }

    // Diğer işlemler ve sorgular eklenebilir
}
