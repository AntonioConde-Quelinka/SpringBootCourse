package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.repository.entity.Alumno;

/**
 * Interactua con la DDBB
 */
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

	// TODO: Nuevas operaciones de consulta de la BBDD. Ejemplos de...
	
	// 1.- Keyword queries
	
	// obtener alumnos por rango de edad
	Iterable<Alumno> findByEdadBetween(int from, int to);
	
	// TODO: obtener alumnos por nombre (que contengan parte del nombre)
	Iterable<Alumno> findByNombreContaining(String name);
	
	
	// 2.- JPQL 
	
	// 3.- Native queries
	
	// 4.- 'Criteria' API
	
	// 5.- Uso de Store Procedures
}
