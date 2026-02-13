package com.datamate.bedrock.practice.application.usecase;


import com.datamate.bedrock.practice.application.dto.LoginRequest;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import com.datamate.bedrock.practice.infrastructure.security.CustomUserDetails;
import com.datamate.bedrock.practice.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final StudentRepository repository;
    private final JwtService jwtService;

    public LoginUseCase(AuthenticationManager authenticationManager,
                        StudentRepository repository,
                        JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public String execute(LoginRequest request) {
        // 1. Authenticate (This will throw exception if password is wrong)
        System.out.println("1. LoginUseCase started for: " + request.email());
        try{

            System.out.println("2. Calling AuthenticationManager...");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            System.out.println("3. Authentication Successful!");

        }catch (Exception e){
            System.out.println("❌ Authentication Failed: " + e.getMessage());
            throw e;

        }
// Step 2: Fetch User
        System.out.println("4. Fetching user from DB...");
        Email emailObj = new Email(request.email());
        Student student = repository.findByEmail(emailObj).orElseThrow();
        System.out.println("5. User found: " + student.getName());

        // Step 3: Generate Token
        System.out.println("6. Generating Token...");
        String token = jwtService.generateToken(new CustomUserDetails(student));
        System.out.println("7. Token Generated: " + token);

        return token;
//        // 2. If we get here, user is valid. Find them to generate token.
//        // (In a real app, optimize to not query DB twice)
//        Student student = repository.findByEmail(request.email()).orElseThrow();
//
//        // 3. Generate JWT
//        return jwtService.generateToken(new CustomUserDetails(student));
    }


}
