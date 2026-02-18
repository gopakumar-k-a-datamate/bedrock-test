package com.datamate.bedrock.practice.application.usecase;

import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.exception.StudentNotFoundException;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.framework.common.logging.annotation.EnableLogger;
import com.datamate.bedrock.framework.common.logging.service.Logger;
import org.springframework.stereotype.Service;

@Service
public class GetStudentUseCase {

    @EnableLogger
    private Logger logger;

    private final StudentRepository repository;

    public GetStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public Student getStudentById(String id) {
        logger.info("Retrieving student with ID: {}", id);
        Student student = repository.findById(id);
        if (student == null) {
            logger.warn("Student not found with ID: {}", id);
            throw new StudentNotFoundException(id);
        }
        logger.info("Student found: {}", student.getName());
        return student;
    }
}
