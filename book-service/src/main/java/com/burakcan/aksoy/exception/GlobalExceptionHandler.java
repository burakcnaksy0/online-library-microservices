package com.burakcan.aksoy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
	}
}
