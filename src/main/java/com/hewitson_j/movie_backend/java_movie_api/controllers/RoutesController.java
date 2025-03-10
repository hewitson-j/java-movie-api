package com.hewitson_j.movie_backend.java_movie_api.controllers;

import com.hewitson_j.movie_backend.java_movie_api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class RoutesController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String entryRoute(){
        return "Welcome to the Movie API! Use /movies/search, /movies/trending/tv, /movies/trending/movie, /movies/search/tv/:id, or /movies/search/movie/:id to get started.";
    }

    @GetMapping("/testRoute")
    public String testRoute(){
        return "This is a successful route";
    }

    @GetMapping("/trending/movies")
    public ResponseEntity<Object> getTrendingMovies(){
        return movieService.getTrendingMovies();
    }

    @GetMapping("/trending/tv")
    public ResponseEntity<Object> getTrendingTv(){
        return movieService.getTrendingShows();
    }

    @GetMapping("/search/movie/{id}")
    public ResponseEntity<Object> searchMovieById(@PathVariable("id") String id){
        return movieService.getSearchMoviesById(id);
    }

    @GetMapping("/search/tv/{id}")
    public ResponseEntity<Object> searchTvById(@PathVariable("id") String id){
        return movieService.getSearchTvById(id);
    }

    @GetMapping("/search/movies")
    public ResponseEntity<Object> searchMoviesByTitle(@RequestParam("title") String title){;
        return movieService.getSearchMovieByTitle(title);
    }

    @GetMapping("/search/tv")
    public ResponseEntity<Object> searchTvByTitle(@RequestParam("title") String title){;
        return movieService.getSearchTvByTitle(title);
    }
}
