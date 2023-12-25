package com.example.demo.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
     MovieService movieService;
     public static final int MAX_PAGE_SIZE=100;

     @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "imdb_score") String sortBy,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "0") Long categoryId){

         if(size>MAX_PAGE_SIZE){
             size=MAX_PAGE_SIZE;
         }
         if(size<1){
             size=1;
         }

        if(page<0){
            page=0;
        }
         return ResponseEntity.ok(movieService.getMovies(page, size, sortBy,search,categoryId));
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
