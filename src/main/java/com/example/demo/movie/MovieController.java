package com.example.demo.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
     MovieService movieService;

     @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public Iterable<Movie> getMovies(){
        return movieService.getMovies();
    }

    @PostMapping("/create")
    public Movie createMovie(@RequestBody Movie movie){

         return movieService.createMovie(movie);

    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@RequestBody Movie movie,@PathVariable Long id){
         return movieService.updateMovie(movie,id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable("id") Long id){
         movieService.deleteMovie(id);
    }
}
