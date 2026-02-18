package com.datamate.bedrock.practice.presentation.controller;

import com.datamate.bedrock.framework.common.auditing.annotation.AuditLog;
import com.datamate.bedrock.practice.application.dto.LoginRequest;
import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.application.usecase.LoginUseCase;
import com.datamate.bedrock.practice.application.usecase.RegisterStudentUseCase;
import com.datamate.bedrock.practice.domain.valueobject.AuthenticationRequest;
import com.datamate.bedrock.practice.domain.valueobject.AuthenticationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterStudentUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterStudentUseCase registerUseCase, LoginUseCase loginUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }

    @AuditLog(action = "REGISTER_STUDENT", resource = "AUTH", resourceId = "#request.username")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @jakarta.validation.Valid @RequestBody RegisterStudentRequest request) {
        AuthenticationResult result = registerUseCase.execute(request);

        if (result.isSuccess()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("accessToken", result.getToken());
            response.put("refreshToken", result.getRefreshToken());
            response.put("user", Map.of(
                    "id", result.getUserId(),
                    "username", result.getUsername(),
                    "email", result.getUsername()));
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", result.getErrorMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @AuditLog(action = "LOGIN_STUDENT_HTTP", resource = "AUTH", resourceId = "#request.email")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@jakarta.validation.Valid @RequestBody LoginRequest request) {
        AuthenticationRequest authRequest = new AuthenticationRequest(request.email(), request.password());
        AuthenticationResult result = loginUseCase.execute(authRequest);

        if (result.isSuccess()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("accessToken", result.getToken());
            response.put("refreshToken", result.getRefreshToken());
            response.put("tokenType", "Bearer");
            response.put("expiresIn", 3600); // 1 hour
            response.put("user", Map.of(
                    "id", result.getUserId(),
                    "username", result.getUsername(),
                    "email", result.getUsername()));

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", result.getErrorMessage());
            response.put("message", result.getErrorMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
