package com.hewitson_j.movie_backend.java_movie_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class RoutesController {
    @GetMapping("/")
    public String homeRoute(){
        return "Welcome to the Movie API! Use /movies/search, /movies/trending/tv, /movies/trending/movie, /movies/search/tv/:id, or /movies/search/movie/:id to get started.";
    }

    @GetMapping("/testRoute")
    public String testRoute(){
        return "This is a successful route";
    }
}
