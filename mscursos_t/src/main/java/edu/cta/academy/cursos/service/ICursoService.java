package edu.cta.academy.cursos.service;

import java.util.Optional;

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
}
