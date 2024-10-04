package com.SecourityDemo.SecurityProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SecurityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityProjectApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("http://localhost:3000")
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//						.allowedHeaders("*")
//						.allowCredentials(true);
//			}
//		};
//	}
}
