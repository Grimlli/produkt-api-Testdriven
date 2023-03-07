package com.example.produktapi;

import com.example.produktapi.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ProduktapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduktapiApplication.class, args);


		Product firstProduct = new Product(1,"Test",204.0,"test","HEJ","src");

		productList.put(firstProduct.getId(),firstProduct);
	}
	public static Map<Integer, Product> productList = new HashMap<>();
}
