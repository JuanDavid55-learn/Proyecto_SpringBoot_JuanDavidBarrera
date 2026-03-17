package com.s1.sistemaGA_Bodegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SistemaGaBodegasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGaBodegasApplication.class, args);
	}
}
