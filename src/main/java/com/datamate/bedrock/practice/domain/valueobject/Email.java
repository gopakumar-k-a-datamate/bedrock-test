package com.datamate.bedrock.practice.domain.valueobject;

import jakarta.persistence.Column;

public record Email(
        @Column(name="email_address")
        String value
) {
    public Email {
        if (value == null || !value.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
