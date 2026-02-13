package com.datamate.bedrock.practice.presentation.controller;


import com.datamate.bedrock.practice.application.dto.LoginRequest;
import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.application.usecase.LoginUseCase;
import com.datamate.bedrock.practice.application.usecase.RegisterStudentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterStudentUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterStudentUseCase registerUseCase, LoginUseCase loginUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterStudentRequest request) {
        registerUseCase.execute(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = loginUseCase.execute(request);
        return ResponseEntity.ok(token); // Returns the JWT string
    }
}
