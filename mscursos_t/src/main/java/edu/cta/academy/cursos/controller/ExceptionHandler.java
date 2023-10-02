package edu.cta.academy.cursos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase que 'escucha' todas las excepciones. Se configura para escuchar las que nos interesen
 */

@RestControllerAdvice(basePackages = {"edu.cta.academy.cursos"})
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
	public ResponseEntity<?> defaultExceptionHandler(Throwable e) {
		ResponseEntity<?> response = null;
		response = ResponseEntity.internalServerError().body(e.getMessage());
		return response;
	}
}
