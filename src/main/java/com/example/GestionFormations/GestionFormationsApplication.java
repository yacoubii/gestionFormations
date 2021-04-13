package com.example.GestionFormations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class GestionFormationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionFormationsApplication.class, args);
	}
}