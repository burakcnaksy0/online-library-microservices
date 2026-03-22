package com.burakcan.aksoy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
		
	@ExceptionHandler(LibraryNotFoundException.class)
	public ResponseEntity<?> handleLibraryNotFoundException(LibraryNotFoundException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ExceptionMessage> handleBookNotFoundException(BookNotFoundException exception){
		return new ResponseEntity<>(exception.getExceptionMessage(),HttpStatus.resolve(exception.getExceptionMessage().status()));
	}
}
