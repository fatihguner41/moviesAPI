package com.example.demo.movielists;

import com.example.demo.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("movielists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieList {
    @Id
    private Long id;
    private String name;
    @Column("user_id")
    private Long userId;
    @MappedCollection(idColumn = "movielist_id")
    private Set<MovieMovieList> movies = new HashSet<>();

    public void deleteMovie(Movie movie){
        for(MovieMovieList m : movies){
            if(m.getMovieId().equals(movie.getId())){
                movies.remove(m);
                break;
            }
        }
    }
}
