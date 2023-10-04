package edu.cta.academy.cursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;
import edu.cta.academy.cursos.repository.CursoRepository;

@Service
public class CursoService implements ICursoService {
	
	@Autowired
	CursoRepository repo;
	
	@Override
	@Transactional
	public Curso newCourse(Curso nuevoCurso) {
		return repo.save(nuevoCurso);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<Curso> modifyById(Curso curso, Long id) {
		Optional<Curso> resul = Optional.empty();
		var leido = this.findById(id);
		if (leido.isPresent()) {
			var toModify = leido.get();
			BeanUtils.copyProperties(curso, toModify, "id");
			
			// En JPA, por el simple hecho de modificar propiedades de una entidad 'conectada'
			// se guardarán solos los cambios al hacer commit
			resul = Optional.of(toModify);
		}
		return resul;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Curso> findAll() {
		return repo.findAll();
	}

	@Override
	@Transactional
	public Optional<Curso> addMultipleToCourse(List<Alumno> alumnos, Long idCurso) {
		var course = this.findById(idCurso);
		if (course.isPresent()) {
			course.get().setAlumnos(alumnos);
		}
		return course;
	}

	@Override
	@Transactional
	public Optional<Curso> removeFromCourse(Alumno alumno, Long idCurso) {
		var course = this.findById(idCurso);
		if (course.isPresent()) {
			course.get().getAlumnos().remove(alumno);
		}
		return course;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> courseForStudent(Long idAlumno) {
		return repo.courseForStudent(idAlumno);
	}

}
