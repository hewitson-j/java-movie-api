package com.hewitson_j.movie_backend.java_movie_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class JavaMovieApiApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(JavaMovieApiApplication.class, args);
		Environment env = context.getEnvironment();

		String port = env.getProperty("server.port");
		System.out.println("Server running on port " + port);
	}

}
