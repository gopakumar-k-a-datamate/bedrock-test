package com.datamate.bedrock.practice.infrastructure.persistence;

import com.datamate.bedrock.practice.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentJpaRepository extends JpaRepository<Student, UUID> {

    Student findById(String id);

}
