package edu.cta.academy.alumnos.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase que 'escucha' todas las excepciones. Se configura para escuchar las que nos interesen
 */

@RestControllerAdvice(basePackages = {"edu.cta.academy"})
public class GestorExcepciones {

	// Para cada tipo de excepción a gestionar, hacemos un método
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> outOfBoundsHandler(StringIndexOutOfBoundsException e) {
		ResponseEntity<?> response = null;
		response = ResponseEntity.internalServerError().body(e.getMessage());
		return response;
	}
	
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e) {
		ResponseEntity<?> response = null;
		response = ResponseEntity.internalServerError().body(e.getMessage());
		return response;
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> defaultExceptionHandler(Throwable e) {
		ResponseEntity<?> response = null;
		response = ResponseEntity.internalServerError().body(e.getMessage());
		return response;
	}
}
