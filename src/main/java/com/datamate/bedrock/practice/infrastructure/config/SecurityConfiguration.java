package com.datamate.bedrock.practice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.core.Ordered;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.datamate.bedrock.framework.common.security.jwt.filter.JwtAuthenticationFilter;

// @Configuration
// @EnableWebSecurity(debug = true)
public class SecurityConfiguration {

        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthFilter;

        public SecurityConfiguration(
                        AuthenticationProvider authenticationProvider,
                        JwtAuthenticationFilter jwtAuthFilter) {
                this.authenticationProvider = authenticationProvider;
                this.jwtAuthFilter = jwtAuthFilter;
        }

        @Bean
        @org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                                .authorizeHttpRequests(auth -> auth
                                                // 1. ALLOW SWAGGER UI
                                                .requestMatchers(
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll()

                                                // 2. ALLOW AUTH ENDPOINTS (Login/Register)
                                                .requestMatchers("/api/v1/auth/**").permitAll()

                                                // 3. SECURE EVERYTHING ELSE
                                                .anyRequest().authenticated())
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}