package com.ecommerce.abcStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AbcStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbcStoreApplication.class, args);
	}

}
