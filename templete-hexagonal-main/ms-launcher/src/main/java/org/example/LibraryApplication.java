package org.example;

import org.example.adapters.JsonPlaceholderCustomerProviderAdapter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"org.example", "org.exemple"})
@EnableCaching
@EnableBatchProcessing
@EnableScheduling

public class LibraryApplication implements CommandLineRunner {

//	@Autowired
//	JsonPlaceholderCustomerProviderAdapter jsonPlaceholderCustomerProviderAdapter;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		var r = jsonPlaceholderCustomerProviderAdapter.findById(1L);
//		System.out.println("El resultado de consultar el rest en Client Rest es : " + r.get().name());
	}


}
