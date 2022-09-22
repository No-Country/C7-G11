package com.gimnasiolomas.ar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class GimnasioSpringApplication{

//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {
		SpringApplication.run(GimnasioSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData() {
		return (args) -> {
//			String sql = "INSERT INTO User (name, last_name, email, password) VALUES (?, ?, ?, ?)";
//			int result = jdbcTemplate.update(sql, "Esteban", "Casile", "esteban@gmail.com", "123456");
//			if (result > 0) {
//				System.out.println("Agregado");
//			}
//			System.out.println("Gimnasio Lomas");
		};
	}
}
