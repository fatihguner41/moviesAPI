package com.example.demo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<UserDTO> usersDTO= new ArrayList<UserDTO>() ;
        for(User user : users){
            usersDTO.add(new UserDTO(user.getUsername(),user.getEmail()));
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

    // Diğer işlemler ve sorgular eklenebilir
}
