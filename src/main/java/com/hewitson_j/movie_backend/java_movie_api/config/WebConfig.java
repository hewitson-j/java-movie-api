package com.hewitson_j.movie_backend.java_movie_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins(
                "http://localhost:5173",
                "https://hewitson-j.github.io",
                "http://hewitson-j.github.io")
                .allowedMethods("GET")
                .maxAge(3600);
    }
}
