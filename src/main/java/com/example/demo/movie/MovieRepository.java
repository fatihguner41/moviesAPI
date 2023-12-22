package com.example.demo.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Page<Movie> findByNameContainingIgnoreCase(Pageable pageable,String keyword);

    @Query("SELECT m.* FROM movies_categories mc\n" +
            "inner join movies m on m.id=mc.movie_id\n" +
            "\n" +
            "where category_id=:categoryId\n" +
            "\n" +
            "ORDER BY m.imdb_score desc LIMIT :limit OFFSET :offset")
    List<Movie> findMoviesByCategoryId(
            @Param("categoryId") Long categoryId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

}
