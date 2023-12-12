package com.example.demo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void addUser(String username,String password,String email){
        userRepository.addUser(username,password,email);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    // Diğer işlemler ve sorgular eklenebilir
}
