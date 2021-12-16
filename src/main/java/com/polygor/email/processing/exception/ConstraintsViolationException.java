package com.polygor.email.processing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Constraint(s) are violated ...")
public class ConstraintsViolationException extends Exception {

    static final long serialVersionUID = -1212314667543456743L;

    public ConstraintsViolationException(String message) {
        super(message);
    }

}
