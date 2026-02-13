package com.datamate.bedrock.practice.application.usecase;

import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class GetStudentUseCase {

    private final StudentRepository repository;

    public GetStudentUseCase(StudentRepository repository){
        this.repository=repository;
    }

    public Student getStudentById(String id) {
        return repository.findById(id);
    }
}
