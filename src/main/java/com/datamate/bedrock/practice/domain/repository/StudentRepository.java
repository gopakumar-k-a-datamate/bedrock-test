package com.datamate.bedrock.practice.domain.repository;

import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.valueobject.Email;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student student);
    Student findById(String id);
    List<Student> findAll();
    void deleteById(String id);
    Optional<Student> findByEmail(Email email);
}
