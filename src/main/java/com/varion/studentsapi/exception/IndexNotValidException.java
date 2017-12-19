package com.varion.studentsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IndexNotValidException extends RuntimeException {
    public IndexNotValidException(String index) {
        super("Indeksot ne e validen." + index);
    }
}