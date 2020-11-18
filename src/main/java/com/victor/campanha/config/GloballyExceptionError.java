package com.victor.campanha.config;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.victor.campanha.exception.StandardError;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GloballyExceptionError {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFoundException(EntityNotFoundException e) {
		return returnError(e, "Campanha não encontrada", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e) {
		return returnError(e, "Parametros invalidos", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> constraintViolationException(ConstraintViolationException e) {
		return returnError(e, getInvalidPropertiesString(e), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		return returnError(e, getInvalidPropertiesString(e), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> exception(Exception e) {
		return returnError(e, e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	private String getInvalidPropertiesString(ConstraintViolationException e) {
		StringBuilder builder = new StringBuilder();
		
		e.getConstraintViolations()
			.forEach(violation -> 
				builder.append(String.format("%s=%s.", violation.getPropertyPath(), violation.getMessage()))
			);
		
		return builder.toString();
	}
	
	private String getInvalidPropertiesString(MethodArgumentNotValidException e) {
		StringBuilder builder = new StringBuilder();
		
		e.getAllErrors()
			.forEach(error -> 
				builder.append(String.format("%s.",error.getDefaultMessage()))
			);
		
		return builder.toString();
	}
	
	private ResponseEntity<StandardError> returnError(Exception e, String message, HttpStatus httpStatus){
		StandardError error = new StandardError(httpStatus, message);
		
		log.error(e.getMessage());
		log.error(e);
		
		return ResponseEntity.status(httpStatus).body(error);
	}
}
