package com.datamate.bedrock.practice.infrastructure.persistence;

import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import com.datamate.bedrock.practice.domain.valueobject.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;


@Repository
public class StudentRepositoryImpl implements StudentRepository{

    private final StudentJpaRepository studentJpaRepository;

    public StudentRepositoryImpl(StudentJpaRepository studentJpaRepository) {this.studentJpaRepository = studentJpaRepository;}

    @Override
    public void save(Student student) {

        studentJpaRepository.save(student);

    }

    @Override
    public Student findById(String id) {

        return studentJpaRepository.findById(UUID.fromString(id)) .orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return studentJpaRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        try {
            studentJpaRepository.deleteById(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            // Log error or ignore if ID is invalid format
        }
    }

    @Override
    public Optional<Student> findByEmail(Email email){

          return  studentJpaRepository.findByEmail(email);

    }


}
