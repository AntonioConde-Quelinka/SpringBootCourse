package edu.cta.academy.alumnos.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.alumnos.repository.entity.Alumno;

/**
 * Interactua con la DDBB
 */
@Repository
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

	// TODO: Nuevas operaciones de consulta de la BBDD. Ejemplos de...
	
	// 1.- Keyword queries
	
	// obtener alumnos por rango de edad
	Iterable<Alumno> findByEdadBetween(int from, int to);
	Page<Alumno> findByEdadBetween(int from, int to, Pageable pageable);
	
	// obtener alumnos por nombre (que contengan parte del nombre)
	Iterable<Alumno> findByNombreContaining(String name);
	
	// 2.- JPQL -    -> Similar a Sql. Se emplea el nombre de entidades y propiedades de ellas
	@Query(value = "select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	Iterable<Alumno> findInNombreOrApellidoJQLP(String pattern);
	
	// 3.- Native queries
	@Query(value = "select * from alumnos a where a.nombre like %?1% or a.apellido like %?1%", nativeQuery = true)
	Iterable<Alumno> findInNombreOrApellidoNative(String pattern);
	
	// 4.- 'Criteria' API (no lo vemos)
	
	// 5.- Uso de Store Procedures (ya definidos)
	//		- Referenciar los procedimientos en la entidad Alumno
	//		- Métodos en AlumnoRepository, con notación @Procedure que usen lo anterior
	@Procedure(name = "Alumno.alumnosRegistradosHoy")
	Iterable<Alumno> procAlumnosAltaHoy();
	
	// Cuando devuelve varios valores, se guarda en un Map, con clave = nombre parametro, valor = su resultado
	@Procedure(name="Alumno.alumnosEdadMediaMinMax")
	Map<String, Number> procEstadisticosEdad(int edadmax, int edadmin, Float edadmedia);
	
}
