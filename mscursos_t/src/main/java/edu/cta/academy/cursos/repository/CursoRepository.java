package edu.cta.academy.cursos.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.comun.entity.Curso;

@Repository
public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {

}
