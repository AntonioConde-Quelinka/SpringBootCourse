package edu.cta.academy.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsalumnosTApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosTApplication.class, args);
	}

}
