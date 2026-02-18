package com.datamate.bedrock.practice.presentation.controller;

import com.datamate.bedrock.practice.application.dto.RegisterStudentRequest;
import com.datamate.bedrock.practice.application.dto.UpdateStudentRequest;
import com.datamate.bedrock.practice.application.usecase.DeleteStudentUseCase;
import com.datamate.bedrock.practice.application.usecase.GetStudentUseCase;
import com.datamate.bedrock.practice.application.usecase.RegisterStudentUseCase;
import com.datamate.bedrock.practice.application.usecase.UpdateStudentUseCase;
import com.datamate.bedrock.practice.domain.entity.Student;
import com.datamate.bedrock.framework.common.auditing.annotation.AuditLog;
import com.datamate.bedrock.framework.common.monitoring.service.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final RegisterStudentUseCase registerStudentUseCase;
    private final GetStudentUseCase getStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final MetricsService metricsService;
    private final MessageSource messageSource;

    public StudentController(RegisterStudentUseCase registerStudentUseCase,
            GetStudentUseCase getStudentUseCase,
            DeleteStudentUseCase deleteStudentUseCase,
            UpdateStudentUseCase updateStudentUseCase,
            MetricsService metricsService,
            MessageSource messageSource) {
        this.registerStudentUseCase = registerStudentUseCase;
        this.getStudentUseCase = getStudentUseCase;
        this.deleteStudentUseCase = deleteStudentUseCase;
        this.updateStudentUseCase = updateStudentUseCase;
        this.metricsService = metricsService;
        this.messageSource = messageSource;
    }

    // @PostMapping
    // public String register(@RequestBody RegisterStudentRequest request) {
    // registerStudentUseCase.execute(request);
    // return "Student registered successfully!";
    // }

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/greet")
    public String greet() {
        return messageSource.getMessage("student.greeting", null,
                LocaleContextHolder.getLocale());
    }

    @AuditLog(action = "GET_STUDENT", resource = "STUDENT", resourceId = "#id")
    @GetMapping("/{id}")
    public Student getById(@PathVariable String id) {
        logger.info("StudentController: getById called with id: {}", id);
        metricsService.incrementCounter("bedrock.student.get.requests");
        try {
            Student student = getStudentUseCase.getStudentById(id);
            metricsService.incrementCounter("bedrock.student.get.success");
            return student;
        } catch (Exception e) {
            metricsService.incrementCounter("bedrock.student.get.error");
            throw e;
        }
    }

    @AuditLog(action = "DELETE_STUDENT", resource = "STUDENT", resourceId = "#id")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        deleteStudentUseCase.execute(id);
        return "Student deleted successfully";
    }

    @AuditLog(action = "UPDATE_STUDENT", resource = "STUDENT", resourceId = "#id")
    @PutMapping("/{id}")
    public String updateStudent(@PathVariable String id, @RequestBody UpdateStudentRequest request) {
        updateStudentUseCase.execute(id, request);
        return "Student updated successfully";
    }

}
