package com.datamate.bedrock.practice.application.usecase;


import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import org.springframework.stereotype.Service;

@Service
public class RegisterStudentUseCase {
    private final StudentRepository repository;

    public RegisterStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(RegisterStudentRequest request) {
        // 1. Convert primitive String to Value Object (Validation happens here)
        Email email = new Email(request.email());

        // 2. Create the Domain Entity
        Student student = new Student(request.name(), email);

        // 3. Persist using the interface (we don't know it's a DB yet)
        repository.save(student);

        System.out.println("Student registered with ID: " + student.getId());
    }

}
