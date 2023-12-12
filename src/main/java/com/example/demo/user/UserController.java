package com.example.demo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public User getUser(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @PostMapping
    public String addUser(@RequestBody User user){
        try {
            userService.addUser(user.getUsername(),user.getPassword(),user.getEmail());
        }catch(Exception e){
            return e.getMessage();
        }
        return "Kullanıcı oluşturuldu.";
    }
}
