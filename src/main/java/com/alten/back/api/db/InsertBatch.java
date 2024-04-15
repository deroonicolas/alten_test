package com.alten.back.api.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alten.back.api.model.Product;
import com.alten.back.api.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InsertBatch {

	@Autowired
	ProductService productService;

	public void insertJsonInDatatbase(final String resource) {

		// Read json file and write to db
		final ObjectMapper mapper = new ObjectMapper();
		final TypeReference<List<Product>> typeReference = new TypeReference<>(){};
		final InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
		try {
			final List<Product> products = mapper.readValue(inputStream, typeReference);
			productService.saveProducts(products);
			log.info("Products saved if not exist");
		} catch (IOException e) {
			log.error("Unable to save products : " + e.getMessage());
		}

	}

}
