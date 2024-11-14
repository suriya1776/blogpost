package com.org.blogpost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle UserLockedException
	@ExceptionHandler(UserLockedException.class)
	public ResponseEntity<String> handleUserLockedException(UserLockedException ex) {
		// Log the exception (optional)
		System.out.println("User locked exception: " + ex.getMessage());

		// Return a 423 Locked status code with the exception message
		return ResponseEntity.status(HttpStatus.LOCKED).body(ex.getMessage());
	}

	// General exception handler for other unexpected errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		// Log the exception (optional)
		System.out.println("General exception: " + ex.getMessage());

		// Return a 500 Internal Server Error with the exception message
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
	}
}
