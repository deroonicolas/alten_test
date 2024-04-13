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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alten.back.api.exception.ProductNotFoundException;
import com.alten.back.api.model.Product;
import com.alten.back.api.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * Create - Create a new product
	 *
	 * @param product - A product object
	 * @return The product object saved
	 */
	@Tag(name = "POST", description = "POST method of Product API")
	@Operation(summary = "Create a product",
    	description = "Create a product. The response is the created Product object")
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Parameter(
		       description = "Id of product to be retrieved", required = true)
			@Valid @RequestBody final Product product) {
		final Product productSaved = productService.saveProduct(product);
		if (Objects.isNull(productSaved)) {
			log.error("Product null");
			return ResponseEntity.noContent().build();
		}
		log.info("Product created with id " + productSaved.getId());
		return new ResponseEntity<>(productSaved, HttpStatus.CREATED);	
	}

	/**
	 * Read - Retrieve all products
	 *
	 * @return An Iterable object of Product full filled
	 */
	@Tag(name = "GET", description = "GET methods of Product API")
	@Operation(summary = "Retrieve all products",
		description = "Retrieve all products. The response is a list of Product objects")
	@GetMapping("/products")
	public ResponseEntity<Iterable<Product>> getProducts() {
		Iterable<Product> products = productService.getProducts();
		log.info("Products retrieved");
		return ResponseEntity.ok(products);
	}

	/**
	 * Read - Retrieve details for product
	 *
	 * @param id - The id of the product
	 * @return An Product object full filled
	 */
	@Tag(name = "GET", description = "GET methods of Product API")
	@Operation(summary = "Retrieve a product",
		description = "Retrieve a product. The response is the Product object")
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@Parameter(
		       description = "Id of product to retrieve", required = true)
			@PathVariable("id") final Long id) {
		final Optional<Product> product = productService.getProduct(id);
		if (product.isPresent()) {
			log.info("Product " + id + " retrieved");
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
	@SuppressWarnings("null")
	@Tag(name = "PATCH", description = "PATCH method of Product API")
	@Operation(summary = "Update details of a product",
		description = "Update details of product. The response is the updated Product object")
	@PatchMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@Parameter(
		       description = "Id of product to update", required = true)
			@PathVariable("id") final Long id, @RequestBody final Map<Object, Object> fields) throws IllegalArgumentException, IllegalAccessException {
		final Optional<Product> receivedProduct = productService.getProduct(id);
		if (receivedProduct.isPresent()) {
			Product product = receivedProduct.get();
			// Get the received keys/values and update the product
			StringBuilder keysValues = new StringBuilder();
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Product.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, product, value);
				field.setAccessible(false);
				keysValues.append(" field : " + key + " / value : " + value + "; ");
			});
			// Save the product with the new properties
			Product productSaved = productService.saveProduct(product);
			log.info("Product " + id + " patched with fields ->" + keysValues);
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
	@Tag(name = "DELETE", description = "DELETE method of Product API")
	@Operation(summary = "Delete a product",
		description = "Delete a product. The response is the Id of the deleted Product")
	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@Parameter(
		       description = "Id of product to delete", required = true)
			@PathVariable("id") final Long id) {
		final Optional<Product> product = productService.getProduct(id);
		if (product.isPresent()) {
			productService.deleteProduct(id);
			log.info("Product " + id + " deleted");
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
