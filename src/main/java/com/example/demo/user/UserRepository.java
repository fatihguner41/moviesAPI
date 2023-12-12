package com.example.demo.user;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE username = :username")
    User findByUsername(String username);

    @Modifying
    @Query("INSERT INTO users (username, password, email, created_at) VALUES (:username, :password, :email, CURRENT_TIMESTAMP)")
    void addUser(String username, String password, String email);
}
