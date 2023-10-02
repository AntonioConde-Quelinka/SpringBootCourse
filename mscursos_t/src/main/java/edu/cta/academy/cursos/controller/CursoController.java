package edu.cta.academy.cursos.controller;

import java.awt.print.Book;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.cursos.repository.entity.Curso;
import edu.cta.academy.cursos.service.ICursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

	// Se usa el paquete 'fachada' de org.slf4j.xxxx
	Logger logger = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	ICursoService service;
	
	@PostMapping
	public ResponseEntity<?> newCourse(@Valid @RequestBody Curso curso, BindingResult br) {

		if (br.hasErrors()) {
			return generateCursoError(br);

		} else {
			logger.debug("Curso OK");
			var nuevoAlumno = this.service.newCourse(curso);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
		}
	}

	// Baja
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable long id) {
		this.service.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}") // GET http://localhost:8081/alumno/{id}
	public ResponseEntity<?> findById(@PathVariable long id) {
		var curso = service.findById(id);
		if (curso.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(curso.get());
		}
	}

	@GetMapping // GET http://localhost:8081/alumno
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> response = null;
		Iterable<Curso> resul = null;

		resul = service.findAll();
		response = ResponseEntity.ok(resul);

		return response;
	}

	// Función para devolver un error 400 en caso de fallo de validación de Alumno
	private ResponseEntity<?> generateCursoError(BindingResult br) {

		ResponseEntity<?> responseEntity = null;

		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach(error -> logger.error(error.toString()));

		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		return responseEntity;
	}
}
