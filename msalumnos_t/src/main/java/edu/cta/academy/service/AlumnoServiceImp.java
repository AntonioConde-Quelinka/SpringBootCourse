package edu.cta.academy.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.repository.AlumnoRepository;
import edu.cta.academy.repository.entity.Alumno;

/**
 * Implementación de los servicios de la aplicación
 */
@Service
public class AlumnoServiceImp implements AlumnoService {
	
	@Autowired			// Con esto inyectamos instancia creada automáticamente por Spring
	AlumnoRepository alumnoRepository;

	@Override
	@Transactional
	public Alumno nuevoAlumno(Alumno nuevoAlumno) {
		return this.alumnoRepository.save(nuevoAlumno);
	}

	@Override
	@Transactional
	public void borrarPorId(Long id) {
		this.alumnoRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<Alumno> modificarPorId(Alumno alumno, Long id) {
		Optional<Alumno> resul = Optional.empty();
		var leido = this.consultaPorId(id);
		if (leido.isPresent()) {
			var toModify = leido.get();
			BeanUtils.copyProperties(alumno, toModify, "id", "creadoEn");
			
			// En JPA, por el simple hecho de modificar propiedades de una entidad 'conectada'
			// se guardarán solos los cambios al hacer commit
			resul = Optional.of(toModify);
		}
		return resul;
	}

	@Override
	@Transactional(readOnly = true)			// Esto NO generará bloqueos al leer
	public Optional<Alumno> consultaPorId(Long id) {
		return this.alumnoRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> consultarTodos() {
		return this.alumnoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findByEdadBetween(int from, int to) {
		return this.alumnoRepository.findByEdadBetween(from, to);
	}
	
	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findByNombreContaining(String name) {
		return this.alumnoRepository.findByNombreContaining(name);
	}
}
