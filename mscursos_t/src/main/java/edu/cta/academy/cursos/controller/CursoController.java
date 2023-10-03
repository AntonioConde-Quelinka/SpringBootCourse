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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;
import edu.cta.academy.cursos.service.ICursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

	// Se usa el paquete 'fachada' de org.slf4j.xxxx
	Logger logger = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	ICursoService service;
	
	// Alta (nuevo)
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
	
	// Modificación
	@PutMapping("/{id}")
	public ResponseEntity<?> modifyCourse(@Valid @RequestBody Curso curso, @PathVariable Long id, BindingResult br) {
		
		if (br.hasErrors()) {
			return generateCursoError(br);
			
		} else {
			logger.debug ("CURSO RX " + curso);
			var cursoModificado = this.service.modifyById(curso, id);
			if (cursoModificado.isEmpty()) {
				return ResponseEntity.notFound().build();
			} 
			else {
				return ResponseEntity.ok(cursoModificado);
			}
		} 	
	}

	// Baja
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable long id) {
		this.service.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}") 
	public ResponseEntity<?> findById(@PathVariable long id) {
		var curso = service.findById(id);
		if (curso.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(curso.get());
		}
	}

	@GetMapping 
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> response = null;
		Iterable<Curso> resul = null;

		resul = service.findAll();
		response = ResponseEntity.ok(resul);

		return response;
	}
	
	// Matricular alumno en curso. 
	// PUT ya que, conceptualmente, consideramos que estamos 'modificando' un curso
	@PutMapping("/add-students/{idCurso}")
	public ResponseEntity<?> addMultipleToCourse(
			@RequestBody List<Alumno> alumnos, 
			@PathVariable Long idCurso) 
	{
		ResponseEntity<?> resp = null;
		var courseModified = this.service.addMultipleToCourse(alumnos, idCurso);
		if (courseModified.isPresent()) {
			resp = ResponseEntity.ok(courseModified.get());
		} else {
			resp = ResponseEntity.notFound().build();
		}
		return resp;
	}
	
	// Borrar un alumno de un curso. 
	// PUT ya que, conceptualmente, consideramos que estamos 'modificando' un curso
	@PutMapping("/remove-student/{idCurso}")
	public ResponseEntity<?> removeFromCourse(
			@RequestBody Alumno alumno, 
			@PathVariable Long idCurso) 
	{
		ResponseEntity<?> resp = null;
		var courseModified = this.service.removeFromCourse(alumno, idCurso);
		if (courseModified.isPresent()) {
			resp = ResponseEntity.ok(courseModified.get());
		} else {
			resp = ResponseEntity.notFound().build();
		}
		return resp;
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
