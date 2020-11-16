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
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST, "Campanha não encontrada");
		
		logError(e);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST, "Parametros invalidos");
		
		logError(e);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> constraintViolationException(ConstraintViolationException e) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST, getInvalidPropertiesString(e));
		
		logError(e);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST, getInvalidPropertiesString(e));
		
		logError(e);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> exception(Exception e) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST, e.getMessage());
		
		logError(e);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	private void logError(Exception e) {
		log.error(e.getMessage());
		log.error(e);
	}
	
	private String getInvalidPropertiesString(ConstraintViolationException e) {
		StringBuilder builder = new StringBuilder();
		
		e.getConstraintViolations()
			.forEach(violation -> {
				builder.append(String.format("%s=%s.", violation.getPropertyPath(), violation.getMessage()));
			});
		
		return builder.toString();
	}
	
	private String getInvalidPropertiesString(MethodArgumentNotValidException e) {
		StringBuilder builder = new StringBuilder();
		
		e.getAllErrors()
			.forEach(error -> {
				builder.append(String.format("%s.",error.getDefaultMessage()));
			});
		
		return builder.toString();
	}
}
