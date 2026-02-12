package com.datamate.bedrock.practice.infrastructure.persistence;

import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.practice.domain.repository.StudentRepository;
import org.springframework.stereotype.Repository;



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

        return studentJpaRepository.findById(id);
    }

}
