package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsgatewayTApplication {

	
	/**
	 * PASOS PARA CREAR GATEWAY
	 * 	1.- Nuevo Spring Starter proyect -> Gateway + Eureka Client
	 *  2.- Anotación  @EnableEurekaClient
	 *  3.- Properties -> YAML
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsgatewayTApplication.class, args);
	}

}
