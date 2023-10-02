package edu.cta.academy.alumnos.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.cta.academy.alumnos.repository.AlumnoRepository;
import edu.cta.academy.alumnos.repository.entity.Alumno;
import edu.dta.academy.model.FraseChiquito;

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
	public Page<Alumno> findByEdadBetween(int from, int to, Pageable pageable) {
		return this.alumnoRepository.findByEdadBetween(from, to, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findByNombreContaining(String name) {
		return this.alumnoRepository.findByNombreContaining(name);
	}
	
	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findInNombreOrApellidoNative(String pattern) {
		return this.alumnoRepository.findInNombreOrApellidoNative(pattern);
	}
	
	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findInNombreOrApellidoJPQL(String pattern) {
		return this.alumnoRepository.findInNombreOrApellidoJQLP(pattern);
	}

	@Override
	@Transactional		// En el PA's no va el readonly = true
	public Iterable<Alumno> procAlumnosAltaHoy() {
		return this.alumnoRepository.procAlumnosAltaHoy();
	}

	@Override
	@Transactional
	public Map<String, Number> procEstadisticosEdad() {
		return this.alumnoRepository.procEstadisticosEdad(0, 0, 0f);
	}

	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findAll(Pageable pageable) {
		return this.alumnoRepository.findAll(pageable);
	}

	// No deberíamos hacerlo aqui, sino en un servicio/controller aparte
	@Override
	public Optional<FraseChiquito> fraseAleatoriaChiquito() {
		Optional<FraseChiquito> resul = Optional.empty();
		RestTemplate request = null;
		FraseChiquito frase = null;
		
		request = new RestTemplate();
		frase = request.getForObject("https://chiquitadas.es/api/quotes/avoleorrr", FraseChiquito.class);
		resul = Optional.of(frase);
		
		return resul;
	}
	
}
