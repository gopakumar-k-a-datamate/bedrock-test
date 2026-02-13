package com.datamate.bedrock.practice.application.usecase;

import com.datamate.bedrock.practice.application.dto.UpdateStudentRequest;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateStudentUseCase {

    private final StudentRepository repository;

    public UpdateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(String id, UpdateStudentRequest request) {
        Student student = repository.findById(id);

        if (student == null) {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }

        if (request.name() != null && !request.name().isBlank()) {
            student.updateName(request.name());
        }

        if (request.email() != null && !request.email().isBlank()) {
            student.updateEmail(request.email());
        }

        repository.save(student);
    }

}
