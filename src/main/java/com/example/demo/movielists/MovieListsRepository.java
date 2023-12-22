package com.example.demo.movielists;

import org.springframework.data.repository.CrudRepository;

public interface MovieListsRepository extends CrudRepository<MovieList,Long> {
}
