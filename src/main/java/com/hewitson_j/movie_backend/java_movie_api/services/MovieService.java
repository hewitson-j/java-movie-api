package com.hewitson_j.movie_backend.java_movie_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    public void printApiKey() {
        System.out.println("TMDb API Key: " + apiKey);
    }
}
