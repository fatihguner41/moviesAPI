package com.example.demo.movielists;

import com.example.demo.dto.movielist.AddMoviesToMovieListRequest;
import com.example.demo.dto.movielist.CreateMovieListRequest;
import com.example.demo.dto.movielist.DeleteMoviesFromMovieListRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movielists")
@RequiredArgsConstructor
public class MovieListsController {
    @Autowired
    MovieListsService movieListsService;

    @GetMapping
    public Iterable<MovieList> getMovieLists() {
        return movieListsService.getMovieLists();
    }

    @PostMapping
    public MovieList createMovieList(@RequestBody CreateMovieListRequest createMovieListRequest,
                                     @RequestHeader("Authorization") String bearerToken) {
        String token = extractBearerToken(bearerToken);
        return movieListsService.createMovieList(createMovieListRequest,token);
    }

    @PostMapping("/{id}/movies/add")
    public ResponseEntity<String> addMoviesToMovieList(@RequestBody AddMoviesToMovieListRequest addMoviesToMovieListRequest,
                                               @RequestHeader("Authorization") String bearerToken,
                                               @PathVariable("id") Long movielist_id) throws ClassNotFoundException {
        String token = extractBearerToken(bearerToken);
        try {
            movieListsService.addMoviesToMovieList(addMoviesToMovieListRequest,token,movielist_id);
        } catch (IllegalAccessException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok("Movies are successfully added to list.");
    }

    @PostMapping("/{id}/movies/delete")
    public ResponseEntity<String> deleteMoviesFromMovieList(@RequestBody DeleteMoviesFromMovieListRequest deleteMoviesFromMovieListRequest,
                                                            @RequestHeader("Authorization") String bearerToken,
                                                            @PathVariable("id") Long movielist_id) throws ClassNotFoundException, IllegalAccessException {
        String token = extractBearerToken(bearerToken);
        try {
            movieListsService.deleteMoviesFromMovieList(deleteMoviesFromMovieListRequest,token,movielist_id);
        } catch (ClassNotFoundException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok("Movies are successfully deleted from the list.");
    }

    public String extractBearerToken(String authorizationHeader) {
        // Authorization header'ındaki Bearer token'ını çıkart
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }


}
