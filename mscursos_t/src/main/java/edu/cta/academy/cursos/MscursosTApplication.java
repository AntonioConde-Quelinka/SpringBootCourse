package edu.cta.academy.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan("edu.cta.academy.comun")	// Buscará entidades en el proyecto comun
public class MscursosTApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscursosTApplication.class, args);
	}

}
