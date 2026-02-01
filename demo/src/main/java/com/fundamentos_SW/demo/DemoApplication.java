package com.fundamentos_SW.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.fundamentos_SW.demo")
@EnableJpaRepositories(basePackages = "com.fundamentos_SW.demo.repositorio")
@EntityScan(basePackages = "com.fundamentos_SW.demo.model")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
