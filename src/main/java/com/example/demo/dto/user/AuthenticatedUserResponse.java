package com.example.demo.dto.user;

import com.example.demo.movielists.MovieList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserResponse {
    private String username;
    private String email;
    private List<MovieList> movieLists;
}
