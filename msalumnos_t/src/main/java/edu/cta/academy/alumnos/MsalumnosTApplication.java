package edu.cta.academy.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@EntityScan("edu.cta.academy.comun")					// Buscará entidades en el proyecto comun
//@ComponentScan("pagake.a.componentes.comunes")		// Buscará componentes comunes (services, repositories)
public class MsalumnosTApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosTApplication.class, args);
	}

}
