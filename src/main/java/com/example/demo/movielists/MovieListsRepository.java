package com.example.demo.movielists;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieListsRepository extends CrudRepository<MovieList,Long> {
    @Query("select ml.* from movielists ml\n" +
            "inner join users u on u.user_id=ml.user_id\n" +
            "where u.role='ADMIN'")
    public List<MovieList> getMovieListsFromAdmin();

    @Query("select ml.* from movielists ml\n" +
            "inner join users u on u.user_id=ml.user_id\n" +
            "where u.user_id=:userId")
    public List<MovieList> getMovieListsFromAuthenticatedUser(@Param("userId") Long userId);


}
