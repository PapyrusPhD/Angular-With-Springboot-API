package com.zuitt.coursebooking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class AppController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("result", "error");

        if (exception.getCause() == null) {
            response.put("details", exception.getLocalizedMessage());
        } else if (exception.getCause().getCause() == null) {
            response.put("details", exception.getCause().getLocalizedMessage());
        } else {
            response.put("details", exception.getCause().getCause().getLocalizedMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}