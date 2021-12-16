package com.polygor.email.processing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email is invalid.")
public class InvalidEmailException extends Exception {

    private static final long serialVersionUID = 66666656453452454L;

    public InvalidEmailException(String message) {
        super(message);
    }

}
