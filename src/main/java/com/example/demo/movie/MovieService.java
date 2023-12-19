package com.example.demo.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(Movie movie,Long id) {
        Movie oldMovie = movieRepository.findById(id).orElseThrow();

        if(!movie.getCategories().isEmpty()){
            oldMovie.setCategories(movie.getCategories());
        }
        if(movie.getName()!= null){
            oldMovie.setName(movie.getName());
        }
        if(movie.getDate()!=null){
            oldMovie.setDate(movie.getDate());
        }
        if(movie.getImdbScore()!=null){
            oldMovie.setImdbScore(movie.getImdbScore());
        }
        if(movie.getIncome()!=null){
            oldMovie.setIncome(movie.getIncome());
        }
        if(movie.getDescription()!=null){
            oldMovie.setDescription(movie.getDescription());
        }

        return movieRepository.save(oldMovie);


    }
}
