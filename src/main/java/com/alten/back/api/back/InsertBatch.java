package com.alten.back.api.back;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alten.back.api.model.Product;
import com.alten.back.api.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InsertBatch {

	Logger logger = LoggerFactory.getLogger(InsertBatch.class);

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
			logger.info("Products Saved !");
		} catch (IOException e) {
			logger.error("Unable to save products : " + e.getMessage());
		}

	}

}
