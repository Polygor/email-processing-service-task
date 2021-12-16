package com.polygor.email.processing.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find entity with id.")
public class EntityNotFoundException extends Exception {

    static final long serialVersionUID = -56574634576745345L;

    public EntityNotFoundException(String message) {
        super(message);
    }

}
