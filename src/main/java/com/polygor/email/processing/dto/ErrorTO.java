package com.polygor.email.processing.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ErrorTO {

    @Getter
    @Setter
    private HttpStatus status;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private List<String> errors;


    public ErrorTO(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
    }


    public ErrorTO(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}