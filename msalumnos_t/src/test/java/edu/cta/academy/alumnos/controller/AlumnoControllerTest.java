package edu.cta.academy.alumnos.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import edu.cta.academy.comun.entity.Alumno;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AlumnoControllerTest {
	
	@LocalServerPort
	private int port;				// Inyecta el puerto
	
	@Autowired
	TestRestTemplate restRemplate;	// Cliente HTTP que lanzará las pruebas
	
	@BeforeAll
	public static void antesTodosTest() {
		System.out.println("Antes de todo");
	}
	
	@AfterAll
	public static void despuesTodosTest() {
		System.out.println("Despues de todo");
	}
	
	@BeforeEach
	public  void antesDeCadaTest() {
		System.out.println("Antes de cada uno de los Test");
	}
	
	@AfterEach
	public  void despuesDeCadaTest() {
		System.out.println("Despues de cada uno de los Test");
	}
	
	@Test							// Indica un caso de prueba
	public void testGetAlumnos() {
		Alumno[] arrAlumnos = this.restRemplate
				.withBasicAuth("admin", "zaragoza")
				.getForObject("http://localhost:" + port + "/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrAlumnos);
		
		// Aserts
		assertThat(la).doesNotContainNull();
	}
	
	@Test							// Indica un caso de prueba
	public void testGetAlumnosNull() {
		Alumno[] arrAlumnos = this.restRemplate
				.withBasicAuth("admin", "zaragoza")
				.getForObject("http://localhost:" + port + "/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrAlumnos);
		
		// Aserts
		assertThat(la).containsOnlyNulls();
	}
	
	@Test							// Indica un caso de prueba
	public void testGetAlumnos_all_ids_must_be_not_zeroes() {
		Alumno[] arrAlumnos = this.restRemplate
				.withBasicAuth("admin", "zaragoza")
				.getForObject("http://localhost:" + port + "/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrAlumnos);
		
		// Aserts
		assertThat(la).allSatisfy(alumno -> assertThat(alumno.getId()).isNotEqualTo(0l));
		
		// assertThat(la).allMatch(p -> p.getId() != 0);
	}
	
	
	
	
}
