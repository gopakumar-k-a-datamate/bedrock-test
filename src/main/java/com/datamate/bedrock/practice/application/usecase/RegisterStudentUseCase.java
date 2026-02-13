package com.datamate.bedrock.practice.application.usecase;


import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class RegisterStudentUseCase {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    public RegisterStudentUseCase(StudentRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(RegisterStudentRequest request) {

        Email email = new Email(request.email());

        String encodedPassword = passwordEncoder.encode(request.password());

        Student student = new Student(request.name(), email,encodedPassword);

        repository.save(student);

        System.out.println("Student registered with ID: " + student.getId());
    }

}
