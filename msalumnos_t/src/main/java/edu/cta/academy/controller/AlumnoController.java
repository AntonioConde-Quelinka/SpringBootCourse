package edu.cta.academy.controller;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.repository.entity.Alumno;
import edu.cta.academy.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Recibe HTTP Request y devuelve HTTP Responses
 */
@RestController
@RequestMapping("/alumno")
public class AlumnoController  {
	
	// Se usa el paquete 'fachada' de org.slf4j.xxxx
	Logger logger = LoggerFactory.getLogger(AlumnoController.class);
	
	@Autowired
	AlumnoService service;
	
	@Operation(summary = "Crea un nuevo alumno en la BBDD CTA Academy")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Alumno creado correctamente",
			content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}	
		)
	})
	@PostMapping
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br) {
		
		if (br.hasErrors()) {
			return generateAlumnoError(br);
			
		} else {
			logger.debug("Alumno OK");
			var nuevoAlumno = this.service.nuevoAlumno(alumno);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
		}
	} 
	
	// Baja
	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarAlumno(@PathVariable long id) {
		this.service.borrarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")		// GET http://localhost:8081/alumno/{id}
	public ResponseEntity<?> listarAlumno(@PathVariable long id) {
		var alumno = service.consultaPorId(id);
		if (alumno.isEmpty()) {
			return ResponseEntity.noContent().build();
		} 
		else {
			return ResponseEntity.ok(alumno.get());
		} 	
	}

	@GetMapping		// GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnos() {
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.consultarTodos();
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/contienenombre/{name}")		
	public ResponseEntity<?> listarAlumnosNombre (@PathVariable String name) 
	{
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.findByNombreContaining(name);
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/edades")		
	public ResponseEntity<?> listarAlumnosEdades(
			@RequestParam(required = true, name="edadmin") int desde, 
			@RequestParam(required = true, name="edadmax") int hasta) 
	{
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.findByEdadBetween(desde, hasta);
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, @PathVariable Long id, BindingResult br) {
		
		if (br.hasErrors()) {
			return generateAlumnoError(br);
			
		} else {
			logger.debug ("ALUMNO RX " + alumno);
			var alumnoModificado = this.service.modificarPorId(alumno, id);
			if (alumnoModificado.isEmpty()) {
				return ResponseEntity.notFound().build();
			} 
			else {
				return ResponseEntity.ok(alumnoModificado);
			}
		} 	
	} 
	
	@GetMapping("/test")   // GET http://localhost:8080/alumno/test
	public Alumno obtenerAlumnoTest() {
		Alumno resul = null;
		// Entidad alumno en estado 'transient' (no está conectado a BBDD)
		resul = new Alumno((long) 51, "Fereshteh", "Lopez", "fere@oracle.es", 18, LocalDateTime.now());
		return resul;
	}

	// Función para devolver un error 400 en caso de fallo de validación de Alumno
	private ResponseEntity<?> generateAlumnoError(BindingResult br) {
		
		ResponseEntity<?> responseEntity = null;
		
		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach( error -> logger.error(error.toString()));

		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		return responseEntity;
	}
}
