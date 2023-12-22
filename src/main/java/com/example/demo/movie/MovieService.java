package com.example.demo.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable pageable= PageRequest.of(page,size,Sort.by(sortBy).descending());
        if(categoryId!=0L){
            return movieRepository.findMoviesByCategoryId(categoryId,size,page*size);
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
