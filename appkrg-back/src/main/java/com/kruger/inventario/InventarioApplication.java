package com.kruger.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventarioApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/sivac");
		SpringApplication.run(InventarioApplication.class, args);
	}

}
