package edu.cta.academy.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MscursosTApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscursosTApplication.class, args);
	}

}
