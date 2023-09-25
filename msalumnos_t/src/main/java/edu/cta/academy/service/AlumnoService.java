package edu.cta.academy.service;

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
}
