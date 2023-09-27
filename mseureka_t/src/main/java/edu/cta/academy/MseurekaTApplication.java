package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer		// Activamos Eureka
public class MseurekaTApplication {
	
	/**
	 * 1) Creamos Spring Starter y dpendencia Eureka Server
	 * 2) Add dependencia jaxb
	 * 3) Anotación @EnableEurekaServer
	 * 4) Config properties 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(MseurekaTApplication.class, args);
	}

}
