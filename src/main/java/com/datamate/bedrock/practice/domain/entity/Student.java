package com.datamate.bedrock.practice.domain.entity;

import com.datamate.bedrock.practice.domain.valueobject.Email;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="students")
public class Student {

    @Id
    private  UUID id;
    private String name;

    @Embedded
    private Email email;

    protected Student() {
    }

    // Constructor for creating a new student
    public Student(String name, Email email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    // Business Logic can go here
    public void updateName(String newName) {
        if(newName.isBlank()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = newName;
    }

    public void updateEmail(Email newEmail){
        if(newEmail==null) throw new IllegalArgumentException("Email cannot be empty");
        this.email = newEmail;
    }

    public void updateEmail(String emailString) {
        // Validation happens inside the Email constructor automatically!
        this.updateEmail(new Email(emailString));
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email.value(); }
}
