package com.projmanag.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	//Custom Exception for double Project Identifier
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectIdException (ProjectIdException ex, WebRequest request) {
		
		ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
		
		return new ResponseEntity<Object>(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);	
	}

	//Custom Exception for project not found
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectNtoFoundException (ProjectNotFoundException ex, WebRequest request) {
		
		ProjectNotFoundExceptionResponse projectIdExceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
		
		return new ResponseEntity<Object>(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);	
	}
	
	//Custom Exception for double username (email)
	@ExceptionHandler
	public final ResponseEntity<Object> handleUsernameAlreadyExistsException (UsernameAlreadyExistsException ex, WebRequest request) {
		
		UsernameAlreadyExistsResponse usernameAlreadyExistsResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
		
		return new ResponseEntity<Object>(usernameAlreadyExistsResponse, HttpStatus.BAD_REQUEST);	
	}
}
