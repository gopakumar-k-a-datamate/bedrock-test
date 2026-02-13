package com.datamate.bedrock.practice.application.usecase;

import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteStudentUseCase {
    private final StudentRepository repository;

    public DeleteStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        repository.deleteById(id);
    }
}
