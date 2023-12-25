package com.example.demo.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public  Iterable<Movie> getMovies(int page, int size, String sortBy, String search, Long categoryId){
        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Movie> movies;
        if(categoryId!=0) {
            switch (sortBy) {
                case "imdb_score":
                    movies = movieRepository.findMoviesByCategoryIdOrderByImdbScore(categoryId);
                    break;
                case "income":
                    movies = movieRepository.findMoviesByCategoryIdOrderByIncome(categoryId);
                    break;
                case "date":
                    movies = movieRepository.findMoviesByCategoryIdOrderByDate(categoryId);
                    break;
                default:
                    movies=movieRepository.findMoviesByCategoryIdOrderByImdbScore(categoryId);
                    break;
            }
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), movies.size());

            return new PageImpl<>(movies.subList(start, end), pageable, movies.size());

        }
        return movieRepository.findByNameContainingIgnoreCase(pageable,search);
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
