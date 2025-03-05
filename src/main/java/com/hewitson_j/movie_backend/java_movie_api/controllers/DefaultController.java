package com.hewitson_j.movie_backend.java_movie_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DefaultController {
    @GetMapping("/")
    public RedirectView redirectToMovieRoutes(){
        return new RedirectView("/movies/");
    }
}
