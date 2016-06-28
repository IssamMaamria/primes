package com.maamria.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler.
 *
 * @author maamria
 *         <p/> 28/06/2016
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    /**
     * Handles exceptions.
     * @param ex exception
     * @return the response
     */
    @ExceptionHandler
    public ResponseEntity<?> handle(Exception ex){
        if(ex instanceof MethodArgumentTypeMismatchException){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
