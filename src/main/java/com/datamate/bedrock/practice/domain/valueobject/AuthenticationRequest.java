package com.datamate.bedrock.practice.domain.valueobject;

public record AuthenticationRequest(String username, String password) {
    public AuthenticationRequest {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
}
