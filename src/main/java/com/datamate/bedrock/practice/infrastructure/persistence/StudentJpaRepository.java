package com.datamate.bedrock.practice.infrastructure.persistence;

import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentJpaRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByEmail(Email email);
}
