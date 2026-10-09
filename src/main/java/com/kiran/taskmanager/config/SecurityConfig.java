package com.kiran.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kiran.taskmanager.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Connecting JwtAuthenticationFilter to SecurityConfig
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        
        	//Enable CORS handling
        	.cors(Customizer.withDefaults())
        
            // Disable CSRF because we are using JWT-based authentication
            .csrf(csrf -> csrf.disable())

            // JWT authentication is stateless
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Define which endpoints are public/protected
            .authorizeHttpRequests(auth -> auth
            		
            	//Allow browser preflight requests
            	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()	

                // Registration and login do not require JWT
                .requestMatchers(
                    "/api/auth/register",
                    "/api/auth/login"
                ).permitAll()

                // All task APIs require authentication
                .requestMatchers("/api/tasks/**").authenticated()

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // Run our JWT filter before Spring Security's username/password filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}