package com.alten.back.api.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alten.back.api.model.Product;
import com.alten.back.api.service.ProductService;
import com.alten.backi.api.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * Create - Create a new product
	 *
	 * @param product - A product object
	 * @return The product object saved
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody final Product product) {
		final Product productSaved = productService.saveProduct(product);
		if (Objects.isNull(productSaved)) {
			return ResponseEntity.noContent().build();
		}
		return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
	}

	/**
	 * Read - Retrieve all products
	 *
	 * @return An Iterable object of Product full filled
	 */
	@GetMapping("/products")
	public ResponseEntity<Iterable<Product>> getProducts() {
		Iterable<Product> products = productService.getProducts();
		return ResponseEntity.ok(products);
	}

	/**
	 * Read - Retrieve details for product
	 *
	 * @param id - The id of the product
	 * @return An Product object full filled
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") final Long id) {
		final Optional<Product> product = productService.getProduct(id);
		if (product.isPresent()) {
			return new ResponseEntity<>(product.get(), HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product with id " + id + " not found.");
		}
	}
	
	/**
	 * Update - Update an existing product
	 *
	 * @param id		- The id of the product to update
	 * @param fields 	- A map of keys / values which represents fields
	 * @return The product object updated
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@PatchMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") final Long id, @RequestBody final Map<Object, Object> fields) throws IllegalArgumentException, IllegalAccessException {
		final Optional<Product> receivedProduct = productService.getProduct(id);
		if (receivedProduct.isPresent()) {
			Product product = receivedProduct.get();
			// Get the received keys/values and update the product
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Product.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, product, value);
				field.setAccessible(false);
			});
			// Save the product with the new properties
			Product productSaved = productService.saveProduct(product);
			return new ResponseEntity<>(productSaved, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product with id " + id + " not found.");
		}
	}

	/**
	 * Delete - Delete a product
	 *
	 * @param id - The id of the product to delete
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") final Long id) {
		final Optional<Product> p = productService.getProduct(id);
		if (p.isPresent()) {
			productService.deleteProduct(id);
			return ResponseEntity.ok("Product with id " + id + " deleted successfully!.");
		} else {
			throw new ProductNotFoundException("Product with id " + id + " not found.");
		}
	}

	/**
	 * Handling exceptions
	 *
	 * @param ex The MethodArgumentNotValidException exception
	 * @return A Map with field name associated to the error message
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}

}
