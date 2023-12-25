package com.example.demo.movielists;

import com.example.demo.dto.movielist.AddMoviesToMovieListRequest;
import com.example.demo.dto.movielist.CreateMovieListRequest;
import com.example.demo.dto.movielist.DeleteMoviesFromMovieListRequest;
import com.example.demo.movie.Movie;
import com.example.demo.movie.MovieRepository;
import com.example.demo.services.JWTService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieListsService {
    @Autowired
    MovieListsRepository movieListsRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    UserRepository userRepository;


    public Iterable<MovieList> getMovieLists(){
        return movieListsRepository.getMovieListsFromAdmin();
    }

    public MovieList createMovieList(CreateMovieListRequest createMovieListRequest,String token){
        String username = jwtService.extractUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow();
        MovieList movieList = new MovieList();
        movieList.setName(createMovieListRequest.getName());
        movieList.setUserId(user.getUser_id());
        return movieListsRepository.save(movieList);
    }

    public void addMoviesToMovieList(AddMoviesToMovieListRequest addMoviesToMovieListRequest,
                                     String token,
                                     Long movielistId) throws ClassNotFoundException, IllegalAccessException {
        String username = jwtService.extractUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow();
        MovieList movieList = movieListsRepository.findById(movielistId).orElseThrow();
        List<Movie> movies = (List<Movie>) movieRepository.findAllById(addMoviesToMovieListRequest.getMovies());
        Set<MovieMovieList> movieMovieListSet = new HashSet<>();

        if(movies.isEmpty())
        {
            throw new ClassNotFoundException("The movies that client send can't be found.");
        }

        for (Movie movie : movies) {
            MovieMovieList movieMovieList = new MovieMovieList();
            movieMovieList.setMovieId(movie.getId());
            movieMovieListSet.add(movieMovieList);
        }
        if(Objects.equals(user.getUser_id(), movieList.getUserId())){
            movieMovieListSet.addAll(movieList.getMovies());
            movieList.setMovies(movieMovieListSet);
            movieListsRepository.save(movieList);
        }else {
            throw new IllegalAccessException("You are not allowed to edit this list.");
        }
    }

    public void deleteMoviesFromMovieList(DeleteMoviesFromMovieListRequest deleteMoviesFromMovieListRequest,
                                          String token, Long movielistId) throws ClassNotFoundException, IllegalAccessException {
        String username = jwtService.extractUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow();
        MovieList movieList = movieListsRepository.findById(movielistId).orElseThrow();
        List<Movie> movies = (List<Movie>) movieRepository.findAllById(deleteMoviesFromMovieListRequest.getMovies());


        if(movies.isEmpty())
        {
            throw new ClassNotFoundException("The movies that client sent can't be found.");
        }

        for (Movie movie : movies) {
            movieList.deleteMovie(movie);
        }
        if(Objects.equals(user.getUser_id(), movieList.getUserId())){

            movieListsRepository.save(movieList);
        }else {
            throw new IllegalAccessException("You are not allowed to edit this list.");
        }
    }
}
