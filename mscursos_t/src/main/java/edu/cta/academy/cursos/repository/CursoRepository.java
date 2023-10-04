package edu.cta.academy.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.comun.entity.Curso;

@Repository
public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {
	// NativeQuery
	@Query(value = "select * from cursos where id = "
			+ "(select curso_id from cursos_alumnos where alumnos_id = ?1)", nativeQuery = true)
	Optional<Curso> courseForStudent(Long idAlumno);
}
