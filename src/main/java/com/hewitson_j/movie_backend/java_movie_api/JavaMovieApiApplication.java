package com.hewitson_j.movie_backend.java_movie_api;

import com.hewitson_j.movie_backend.java_movie_api.services.MovieService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JavaMovieApiApplication {

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(JavaMovieApiApplication.class, args);

		System.out.println("Hello world!");

		MovieService serv = context.getBean(MovieService.class);
		serv.printApiKey();
	}

}
