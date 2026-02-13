package com.datamate.bedrock.practice.application.dto;

public record UpdateStudentRequest(
        String name,
        String email
) {}