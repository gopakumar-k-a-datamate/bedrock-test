package com.datamate.bedrock.practice.domain.exception;

import com.datamate.bedrock.framework.common.exception.exceptions.BusinessException;

public class StudentNotFoundException extends BusinessException {
    public StudentNotFoundException(String id) {
        super("STUDENT_NOT_FOUND", id);
    }
}
