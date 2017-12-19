package com.varion.studentsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudyProgramNotExistingException extends RuntimeException {
    public StudyProgramNotExistingException(String studyProgramName) {
        super("Studiskata programa ne e validna." + studyProgramName);
    }
}