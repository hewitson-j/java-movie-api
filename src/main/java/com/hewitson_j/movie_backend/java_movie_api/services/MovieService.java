package com.hewitson_j.movie_backend.java_movie_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    private URI buildTrendingURI(String route){
        return UriComponentsBuilder.fromUriString(baseUrl + route)
                .queryParam("api_key", apiKey)
                .build()
                .toUri();
    }

    private URI buildSearchByIdURI(String route, String id){
        return UriComponentsBuilder.fromUriString(baseUrl + route + id)
                .queryParam("api_key", apiKey)
                .build()
                .toUri();
    }

    private URI buildSearchByTitle(String route, String title, String page){
        return UriComponentsBuilder.fromUriString(baseUrl + route + title + "&page=" + page)
                .queryParam("api_key", apiKey)
                .build()
                .toUri();
    }

    @Cacheable("trendingMovies")
    public ResponseEntity<Object> getTrendingMovies() {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildTrendingURI("/trending/movie/week");

        try {
            // Get response as a Map (which will be serialized to JSON automatically)
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);

        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data from TMDb"));
        }
    }

    @Cacheable("trendingShows")
    public ResponseEntity<Object> getTrendingShows(){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildTrendingURI("/trending/tv/week");

        try {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data from TMDb"));
        }
    }

    @Cacheable("searchMoviesById")
    public ResponseEntity<Object> getSearchMoviesById(String id){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildSearchByIdURI("/movie/", id);

        try  {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data for Movie " + id + " from TMDb"));
        }
    }

    @Cacheable("searchTvById")
    public ResponseEntity<Object> getSearchTvById(String id){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildSearchByIdURI("/tv/", id);

        try  {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data for TV " + id + " from TMDb"));
        }
    }

    @Cacheable("searchMoviesByTitle")
    public ResponseEntity<Object> getSearchMovieByTitle(String title, String page){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildSearchByTitle("/search/movie?query=", title, page);

        try {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data for movie query from TMDb"));
        }
    }

    @Cacheable("searchTvByTitle")
    public ResponseEntity<Object> getSearchTvByTitle(String title, String page){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildSearchByTitle("/search/tv?query=", title, page);

        try {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch data for TV query from TMDb"));
        }
    }
}
