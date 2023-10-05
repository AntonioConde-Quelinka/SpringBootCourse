package edu.cta.academy.alumnos.controller;

import java.awt.print.Book;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.HttpHeaders;

import edu.cta.academy.alumnos.service.AlumnoService;
import edu.cta.academy.comun.entity.Alumno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Recibe HTTP Request y devuelve HTTP Responses
 */
//@CrossOrigin(
//		originPatterns = {"*"},
//		methods = {RequestMethod.GET})
@RestController
@RequestMapping("/alumno")
public class AlumnoController  {
	
	// Se usa el paquete 'fachada' de org.slf4j.xxxx
	Logger logger = LoggerFactory.getLogger(AlumnoController.class);
	
	@Autowired
	AlumnoService service;
	
	@Value("${instancia}")
	String nombreInstancia;
	
	@Autowired
	Environment enviroment;
	
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
		
		logger.debug("ATENCIDO POR " + nombreInstancia + ", PUERTO: " + enviroment.getProperty("local.server.port") );
		resul = service.consultarTodos();
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/paginados")		// GET http://localhost:8081/alumno/paginados?page=__&size=__
	public ResponseEntity<?> listarAlumnosPaginado(Pageable pageable) {
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.findAll(pageable);
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
			@RequestParam(required = true, name="edadmax") int hasta,
			Pageable pageable
			) 
	{
		ResponseEntity<?> response = null;
		
		var resul = service.findByEdadBetween(desde, hasta, pageable);
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/contiene/{pattern}")		
	public ResponseEntity<?> listarAlumnosContieneEnNombreOApellido (@PathVariable String pattern)
	{
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.findInNombreOrApellidoNative(pattern);;
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/contiene_v2/{pattern}")		
	public ResponseEntity<?> listarAlumnosContieneEnNombreOApellidoJPQL (@PathVariable String pattern)
	{
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.findInNombreOrApellidoJPQL(pattern);;
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/creados-hoy")		
	public ResponseEntity<?> listarAlumnosCreadosHoy ()
	{
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.procAlumnosAltaHoy();;
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/edad-stats")		
	public ResponseEntity<?> estadisticasPorEdad ()
	{
		ResponseEntity<?> response = null;
		
		var resul = service.procEstadisticosEdad();
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
	
	
	@GetMapping("/chiquito")   // GET http://localhost:8080/alumno/test
	public ResponseEntity<?> obtenerFraseChiquito() {
		ResponseEntity<?> responseEntity = null;
		
		// TODO: Completar !!
		var oFrase = this.service.fraseAleatoriaChiquito();
		if (oFrase.isPresent()) {
			responseEntity = ResponseEntity.ok(oFrase.get());
		} else {
			return ResponseEntity.notFound().build();
		}
		
		return responseEntity;
	}
	
	
	// Gestion de Fotos
	
	// 1.- POST con foto
	@PostMapping("/foto")
	public ResponseEntity<?> insertarAlumnoFoto(
			@Valid Alumno alumno, 
			BindingResult br,
			MultipartFile archivo) throws Throwable
	{
		
		if (br.hasErrors()) {
			return generateAlumnoError(br);
			
		} else {
			logger.debug("Alumno OK");
			
			if (!archivo.isEmpty()) {
				logger.debug("Viene con foto");
				try {
					alumno.setFoto(archivo.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.debug("Error al obtener los bytes de la foto. " + e);
					throw e;
				}
			}
			
			var nuevoAlumno = this.service.nuevoAlumno(alumno);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
		}
	} 
	
	// 2.- PUT con foto
	@PutMapping("/foto/{id}")
	public ResponseEntity<?> modificarAlumnoFoto(
			@Valid Alumno alumno,
			@PathVariable Long id, 
			BindingResult br,
			MultipartFile archivo) throws Throwable {
		
		if (br.hasErrors()) {
			return generateAlumnoError(br);
			
		} else {
			logger.debug ("ALUMNO RX " + alumno);
			
			if (!archivo.isEmpty()) {
				logger.debug("Viene con foto");
				try {
					alumno.setFoto(archivo.getBytes());
				} catch (IOException e) {
					logger.error("Error al obteniendo los bytes de la foto para modificar alumno. " + e);
					throw e;
				}
			}
			
			var alumnoModificado = this.service.modificarPorId(alumno, id);
			if (alumnoModificado.isEmpty()) {
				return ResponseEntity.notFound().build();
			} 
			else {
				return ResponseEntity.ok(alumnoModificado);
			}
		} 	
	} 
	
	// 3.- GET foto por ID
	@GetMapping("/foto/{id}")   // GET http://localhost:8080/alumno/test
	public ResponseEntity<?> fotoAlumno(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		
		var alumno = service.consultaPorId(id);
		if (alumno.isEmpty() || alumno.get().getFoto().length <= 0) {
			responseEntity = ResponseEntity.noContent().build();
		} 
		else {
			responseEntity =  ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(alumno.get().getFoto());
		} 
		
		return responseEntity;
	}
	
	// Feign -> llamadas a otro end-point de otro ms de nuestra nube
	@GetMapping("/{idAlumno}/curso-inscrito")
	public ResponseEntity<?> getCursoAlumno(@PathVariable Long idAlumno) {
		ResponseEntity<?> responseEntity = null;
		var curso = this.service.getCursoAlumno(idAlumno);
		if (curso.isPresent()) {
			responseEntity = ResponseEntity.ok(curso.get());
		} else {
			return ResponseEntity.notFound().build();
		}
		return responseEntity;
	}
	
	// Ejemplo de Hateoas
	@GetMapping("/hateoas")		// GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnosHateoas() {
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = service.consultarTodos();
		for (Alumno a :resul)
		{
			a.add(linkTo(methodOn(AlumnoController.class).listarAlumno(a.getId())).withSelfRel());
		}
		
		response = ResponseEntity.ok(resul);
		
		return response;
	}
}
