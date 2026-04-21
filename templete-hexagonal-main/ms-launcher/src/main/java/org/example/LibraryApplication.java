package org.example;

import org.example.adapters.JsonPlaceholderCustomerProviderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.example", "org.exemple"})
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
