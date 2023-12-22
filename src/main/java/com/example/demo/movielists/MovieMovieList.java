package com.example.demo.movielists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("movies_movielists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieMovieList {
    @Column("movie_id")
    private Long movieId;

}
