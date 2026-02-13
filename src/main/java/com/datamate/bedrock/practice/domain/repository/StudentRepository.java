package com.datamate.bedrock.practice.domain.repository;

import com.datamate.bedrock.practice.domain.entity.Student;

import java.util.List;

public interface StudentRepository {
    void save(Student student);
    Student findById(String id);
    List<Student> findAll();
    void deleteById(String id);
}
