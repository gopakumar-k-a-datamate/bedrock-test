package com.datamate.bedrock.practice.infrastructure.config;

import com.datamate.bedrock.practice.domain.repository.StudentRepository;

import com.datamate.bedrock.practice.domain.valueobject.Email;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final StudentRepository repository;

    public ApplicationConfig(StudentRepository repository) {
        this.repository = repository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Find student by email

            Email emailObj = new Email(username);
            // Note: You might need to add findByEmail to your StudentRepository interface!
            var student = repository.findByEmail(emailObj)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new com.datamate.bedrock.practice.infrastructure.security.CustomUserDetails(student);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 1. Initialize the provider (Don't leave it null!)
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // 2. Set the required dependencies
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
