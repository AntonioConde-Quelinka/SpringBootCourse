package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.repository.entity.Alumno;

/**
 * Interactua con la DDBB
 */
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

}
