package edu.cta.academy.alumnos.cliente;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.cta.academy.comun.entity.Curso;

@FeignClient(name = "mscursos")			// Microservicio que consumimos
public interface ICursoFeignClient {
	
	@GetMapping("/curso/for-student/{idAlumno}")
	public Optional<Curso> courseForStudent(@PathVariable Long idAlumno);
}
