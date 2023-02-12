package com.spring.zaritalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZaritalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZaritalkApplication.class, args);
	}

}
