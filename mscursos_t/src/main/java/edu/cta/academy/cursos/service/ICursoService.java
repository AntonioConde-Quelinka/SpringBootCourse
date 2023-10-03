package edu.cta.academy.cursos.service;

import java.util.List;
import java.util.Optional;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;

public interface ICursoService {

	// Alta
	Curso newCourse(Curso nuevoCurso);

	// Baja
	void deleteById(Long id);

	// Modificación
	Optional<Curso> modifyById(Curso curso, Long id);

	// Consulta (uno)
	Optional<Curso> findById(Long id);

	// Consulta (todos)
	Iterable<Curso> findAll();
	
	// Matricular alumno en curso
	Optional<Curso> addMultipleToCourse(List<Alumno> alumnos, Long idCurso);
	
	// Elimina un alumno de un curso
	Optional<Curso> removeFromCourse(Alumno alumno, Long idCurso);
}
