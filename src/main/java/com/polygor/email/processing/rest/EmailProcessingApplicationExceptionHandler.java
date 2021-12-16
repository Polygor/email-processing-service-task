package com.polygor.email.processing.rest;

import com.polygor.email.processing.dto.ErrorTO;
import com.polygor.email.processing.exception.ConstraintsViolationException;
import com.polygor.email.processing.exception.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class EmailProcessingApplicationExceptionHandler {

    private static final String SEMICOLON = ": ";

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ErrorTO> handleInvalidEmail(InvalidEmailException e) {
        ErrorTO to = new ErrorTO(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e.getMessage());
        return new ResponseEntity<>(to, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorTO> handleEntityNotFound(EntityNotFoundException e) {
        ErrorTO errorDTO = new ErrorTO(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintsViolationException.class)
    public ResponseEntity<ErrorTO> handleConstraintViolation(ConstraintsViolationException e) {
        ErrorTO errorDTO = new ErrorTO(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}