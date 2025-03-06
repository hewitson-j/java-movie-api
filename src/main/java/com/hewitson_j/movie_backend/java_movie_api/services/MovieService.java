package com.hewitson_j.movie_backend.java_movie_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class MovieService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private URI buildUri(String route){
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + route)
                .queryParam("api_key", apiKey)
                .build()
                .toUri();;
        return uri;
    }

    public ResponseEntity<Object> getTrendingMovies() {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildUri("/trending/movie/week");

        try {
            // Get response as a Map (which will be serialized to JSON automatically)
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);

        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data from TMDb"));
        }
    }

    public ResponseEntity<Object> getTrendingShows(){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildUri("/trending/tv/week");

        try {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data from TMDB"));
        }
    }
}
