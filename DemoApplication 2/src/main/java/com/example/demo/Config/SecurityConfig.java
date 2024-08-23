package com.example.demo.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
	
    @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf((csrf) -> csrf.disable())
        .addFilterBefore(new CustomHeaderValidationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests((requests)->requests
                .requestMatchers("/users").authenticated()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/signup").permitAll()
                . requestMatchers("/login").permitAll())
        .httpBasic(Customizer.withDefaults())
         .cors(cors -> cors.configurationSource(corsConfigurationSource())); ;
    return http.build();
        
    }

    

    @Bean
   CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://example.com")); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); 
        configuration.setAllowedHeaders(List.of("*")); 
        configuration.setAllowCredentials(true); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

  

}
