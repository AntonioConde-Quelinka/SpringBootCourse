package edu.cta.academy.service;

import java.util.Map;
import java.util.Optional;

import edu.cta.academy.repository.entity.Alumno;

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
	
	Iterable<Alumno> findByNombreContaining(String name);
	
	Iterable<Alumno> findInNombreOrApellidoNative(String pattern);
	
	Iterable<Alumno> findInNombreOrApellidoJPQL(String pattern);
	
	Iterable<Alumno> procAlumnosAltaHoy();
	
	Map<String, Number> procEstadisticosEdad();
}
