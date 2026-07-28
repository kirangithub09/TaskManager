package com.kiran.taskmanager.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//====================VALIDATION ERRORS==============================
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
			MethodArgumentNotValidException ex){
		
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map((FieldError error) -> error.getField() + ":"+error.getDefaultMessage())
				.collect(Collectors.joining(","));
		
		ErrorResponse error= new ErrorResponse(
				400,
				message,
				LocalDateTime.now().toString()
				);
		
		return ResponseEntity.status(400).body(error);
	}
	
	//=========================BUSINESS LOGIC ERRORS==============================
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
		ErrorResponse error = new ErrorResponse(
				400,
				ex.getMessage(),
				LocalDateTime.now().toString()
				);
		return ResponseEntity.status(400).body(error);
	}
	
	//=========================CATCH-ALL========================================
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex){
		ErrorResponse error = new ErrorResponse(
				500,
				//"Something went wrong on the server",
				ex.getMessage(),
				LocalDateTime.now().toString()
				);
		
		return ResponseEntity.status(500).body(error);
	}
}
