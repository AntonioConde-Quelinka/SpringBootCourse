package edu.cta.academy.alumnos.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.cta.academy.comun.entity.Alumno;
//import edu.cta.academy.comun.entity.Alumno;
import edu.dta.academy.model.FraseChiquito;

public interface AlumnoService {
	
	// Alta
	Alumno nuevoAlumno(Alumno nuevoAlumno);
	
	// Baja
	void borrarPorId(Long id); 
	
	// Modificación
	Optional<Alumno> modificarPorId(Alumno alumno, Long id);
	
	// Consulta (uno)
	Optional<Alumno> consultaPorId(Long id);
	
	// Consulta (todos)
	Iterable<Alumno> consultarTodos();
	
	// Consulta por rango de edad
	Iterable<Alumno> findByEdadBetween(int from, int to);
	Page<Alumno> findByEdadBetween(int from, int to, Pageable pageable);
	
	Iterable<Alumno> findByNombreContaining(String name);
	
	Iterable<Alumno> findInNombreOrApellidoNative(String pattern);
	
	Iterable<Alumno> findInNombreOrApellidoJPQL(String pattern);
	
	Iterable<Alumno> procAlumnosAltaHoy();
	
	Map<String, Number> procEstadisticosEdad();
	
	// Consultas páginadas
	Iterable<Alumno> findAll(Pageable pageable);
	
	
	// No deberíamos hacerlo aqui, sino en un servicio/controller aparte
	Optional<FraseChiquito> fraseAleatoriaChiquito();
}
