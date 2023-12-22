package com.example.demo.dto.movielist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMoviesFromMovieListRequest {
    private List<Long> movies;
}
