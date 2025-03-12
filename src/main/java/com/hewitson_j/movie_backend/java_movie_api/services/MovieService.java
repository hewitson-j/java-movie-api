package com.hewitson_j.movie_backend.java_movie_api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MovieService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private static final Logger logger = Logger.getLogger(MovieService.class.getName());
    private static void logErrorUri(URI uri, String errorMessage){
        logger.severe("Request failed for " + uri + ": " + errorMessage);
    }

//    URI formatting methods
    private URI buildTrendingURI(String route, String page){
        return UriComponentsBuilder.fromUriString(baseUrl + route + "?page=" + page)
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

//    Route Handler Methods
    @Cacheable("trendingMovies")
    public ResponseEntity<Object> getTrendingMovies(String page) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildTrendingURI("/trending/movie/week", page);

        try {
            // Get response as a Map (which will be serialized to JSON automatically)
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException e){
            logErrorUri(uri, e.getMessage());
            return catchHttpClientErrorException(e);
        }
        catch (RestClientException e) {
            logErrorUri(uri, e.getMessage());
            return catchRestClientException(e);
        }
    }

    @Cacheable("trendingShows")
    public ResponseEntity<Object> getTrendingShows(String page){
        RestTemplate restTemplate = new RestTemplate();

        URI uri = buildTrendingURI("/trending/tv/week", page);

        try {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            return ResponseEntity.ok(response);
        }
        catch (HttpClientErrorException e){
            logErrorUri(uri, e.getMessage());
            return catchHttpClientErrorException(e);
        }
        catch (RestClientException e) {
            logErrorUri(uri, e.getMessage());
            return catchRestClientException(e);
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
        catch (HttpClientErrorException e){
            logErrorUri(uri, e.getMessage());
            return catchHttpClientErrorException(e);
        }
        catch (RestClientException e) {
            logErrorUri(uri, e.getMessage());
            return catchRestClientException(e);
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
        catch (HttpClientErrorException e){
            logErrorUri(uri, e.getMessage());
            return catchHttpClientErrorException(e);
        }
        catch (RestClientException e) {
            logErrorUri(uri, e.getMessage());
            return catchRestClientException(e);
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
        catch (HttpClientErrorException e){
            logErrorUri(uri, e.getMessage());
            return catchHttpClientErrorException(e);
        }
        catch (RestClientException e) {
            logErrorUri(uri, e.getMessage());
            return catchRestClientException(e);
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
        catch (HttpClientErrorException e){
            logErrorUri(uri, e.getMessage());
            return catchHttpClientErrorException(e);
        }
        catch (RestClientException e) {
            logErrorUri(uri, e.getMessage());
            return catchRestClientException(e);
        }
    }

//    Error handling methods
    private ResponseEntity<Object> catchHttpClientErrorException(HttpClientErrorException e) {
        HttpStatusCode statusCode = e.getStatusCode();
        String statusMessage = "Unknown error";

        logger.severe("Status code: " + statusCode);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> errorResponse = mapper.readValue(e.getResponseBodyAsString(), Map.class);
            statusMessage = (String) errorResponse.getOrDefault("status_message", statusMessage);
        } catch (Exception parseException) {
            logger.severe("Failed to parse error message: " + parseException.getMessage());
        }

        return ResponseEntity.status(statusCode).body(Map.of(
                "status", statusCode.value(),
                "message", statusMessage,
                "success", false));
    }

    private ResponseEntity<Object> catchRestClientException(RestClientException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Failed to fetch data from TMDb",
                        "message", e.getMessage()
                ));
    }
}
