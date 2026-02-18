package com.datamate.bedrock.practice.application.usecase;

import com.datamate.bedrock.framework.common.auditing.annotation.AuditLog;
import com.datamate.bedrock.framework.common.security.vo.UserDetails;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import com.datamate.bedrock.practice.infrastructure.security.CustomUserDetails;
//import com.datamate.bedrock.practice.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
//import com.datamate.bedrock.framework.security.jwt.service.JwtTokenService;
import com.datamate.bedrock.framework.common.logging.annotation.EnableLogger;
import com.datamate.bedrock.framework.common.logging.service.Logger;
import com.datamate.bedrock.framework.common.security.jwt.service.JwtTokenService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.datamate.bedrock.practice.domain.valueobject.AuthenticationRequest;
import com.datamate.bedrock.practice.domain.valueobject.AuthenticationResult;

@Service
public class LoginUseCase {

        @EnableLogger
        private Logger logger;

        private final AuthenticationManager authenticationManager;
        private final StudentRepository repository;
        private final JwtTokenService jwtTokenService;

        public LoginUseCase(AuthenticationManager authenticationManager,
                        StudentRepository repository,
                        JwtTokenService jwtTokenService) {
                this.authenticationManager = authenticationManager;
                this.repository = repository;
                this.jwtTokenService = jwtTokenService;
        }

        @AuditLog(action = "LOGIN_STUDENT", resource = "STUDENT", resourceId = "#request.username")
        public AuthenticationResult execute(AuthenticationRequest request) {
                logger.info("1. LoginUseCase started for: {}", request.username());
                try {
                        logger.debug("2. Calling AuthenticationManager...");
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(request.username(),
                                                        request.password()));
                        logger.info("3. Authentication Successful!");

                } catch (Exception e) {
                        logger.error("❌ Authentication Failed: {}", e.getMessage());
                        return AuthenticationResult.failure("INVALID_CREDENTIALS", "Invalid username or password");
                }

                Email emailObj = new Email(request.username());
                Student student = repository.findByEmail(emailObj)
                                .orElseThrow(() -> new RuntimeException(
                                                "User found during auth but not found in repo?"));

                // Generate Token
                String email = student.getEmail();
                UserDetails bedrockUser = UserDetails.of(
                                student.getId().toString(), // userId
                                email, // username
                                email, // email
                                student.getName(), // firstName
                                "" // lastName
                );

                logger.debug("4. Generating Bedrock Tokens...");
                String accessToken = jwtTokenService.generateAccessToken(bedrockUser);
                // Assuming jwtTokenService has generateRefreshToken, if not I will use
                // accessToken as placeholder or just implement what is available
                String refreshToken = jwtTokenService.generateRefreshToken(bedrockUser);

                logger.info("5. Tokens Generated Successfully");

                return AuthenticationResult.success(accessToken, refreshToken, bedrockUser);
        }

}