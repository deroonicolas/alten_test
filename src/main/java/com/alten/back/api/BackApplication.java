package com.alten.back.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alten.back.api.batch.InsertBatch;

@SpringBootApplication
public class BackApplication implements CommandLineRunner {
	
	@Autowired
	InsertBatch insertBatch;

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		insertBatch.insertJsonInDatatbase();
	}

}
