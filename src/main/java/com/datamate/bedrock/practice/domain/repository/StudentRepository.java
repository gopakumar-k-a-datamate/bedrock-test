package com.datamate.bedrock.practice.domain.repository;

import com.datamate.bedrock.practice.domain.entity.Student;

public interface StudentRepository {
    void save(Student student);
    Student findById(String id);
}
