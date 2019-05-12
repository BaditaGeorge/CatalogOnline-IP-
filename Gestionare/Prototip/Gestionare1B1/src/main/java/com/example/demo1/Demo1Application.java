package com.example.demo1;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Demo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}
        
//        @Bean
//        CorsConfigurationSource corsConfigurationSource() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfiguration corsConfiguration = new CorsConfiguration();
//            corsConfiguration.addAllowedOrigin("http://localhost:3000");
//            corsConfiguration.setAllowedMethods(Arrays.asList(
//                    HttpMethod.GET.name(),
//                    HttpMethod.HEAD.name(),
//                    HttpMethod.POST.name(),
//                    HttpMethod.PUT.name(),
//                    HttpMethod.OPTIONS.name(),
//                    HttpMethod.DELETE.name()));
//            corsConfiguration.addAllowedHeader("*");
//            corsConfiguration.setMaxAge(1800L);
//            source.registerCorsConfiguration("/**", corsConfiguration); // you restrict your path here
//            return source;
//        }
}
