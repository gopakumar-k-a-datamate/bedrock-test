package com.datamate.bedrock.practice.presentation.controller;

import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.application.usecase.RegisterStudentUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final RegisterStudentUseCase registerStudentUseCase;

    public StudentController(RegisterStudentUseCase registerStudentUseCase) {
        this.registerStudentUseCase = registerStudentUseCase;
    }

    @GetMapping()
    public String helloWord(){
        return "hello world";
    }

    @PostMapping
    public String register(@RequestBody RegisterStudentRequest request) {
        registerStudentUseCase.execute(request);
        return "Student registered successfully!";
    }



}
