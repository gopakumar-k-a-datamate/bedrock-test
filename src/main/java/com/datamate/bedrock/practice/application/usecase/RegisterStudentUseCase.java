package com.datamate.bedrock.practice.application.usecase;

import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.datamate.bedrock.practice.domain.valueobject.AuthenticationResult;
import com.datamate.bedrock.framework.common.security.jwt.service.JwtTokenService;
import com.datamate.bedrock.framework.common.security.vo.UserDetails;

@Service
public class RegisterStudentUseCase {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public RegisterStudentUseCase(StudentRepository repository, PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthenticationResult execute(RegisterStudentRequest request) {

        Email email = new Email(request.email());

        if (repository.existsByEmail(email)) {
            return AuthenticationResult.failure("EMAIL_ALREADY_EXISTS", "Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Student student = new Student(request.name(), email, encodedPassword);

        repository.save(student);

        System.out.println("Student registered with ID: " + student.getId());

        // Auto-login: Generate Tokens
        UserDetails bedrockUser = UserDetails.of(
                student.getId().toString(),
                student.getEmail(),
                student.getEmail(),
                student.getName(),
                "");

        String accessToken = jwtTokenService.generateAccessToken(bedrockUser);
        String refreshToken = jwtTokenService.generateRefreshToken(bedrockUser);

        return AuthenticationResult.success(accessToken, refreshToken, bedrockUser);
    }

}
