package com.example.demo.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Page<Movie> findByNameContainingIgnoreCase(Pageable pageable,String keyword);

    @Query("SELECT m.* FROM movies_categories mc " +
            "INNER JOIN movies m ON m.id = mc.movie_id " +
            "WHERE mc.category_id = :categoryId "+
            "ORDER BY m.imdb_score desc"

    )
    List<Movie> findMoviesByCategoryIdOrderByImdbScore(
            @Param("categoryId") Long categoryId);

    @Query("SELECT m.* FROM movies_categories mc " +
            "INNER JOIN movies m ON m.id = mc.movie_id " +
            "WHERE mc.category_id = :categoryId "+
            "ORDER BY m.income desc"

    )
    List<Movie> findMoviesByCategoryIdOrderByIncome(@Param("categoryId") Long categoryId);

    @Query("SELECT m.* FROM movies_categories mc " +
            "INNER JOIN movies m ON m.id = mc.movie_id " +
            "WHERE mc.category_id = :categoryId "+
            "ORDER BY m.date desc"

    )
    List<Movie> findMoviesByCategoryIdOrderByDate(@Param("categoryId") Long categoryId);
}
