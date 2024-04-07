package com.alten.back.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

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
	 * @param employee 	- The product object to update
	 * @return The product object updated
	 */
	@PatchMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") final Long id, @Valid @RequestBody final Product product) {
		final Optional<Product> p = productService.getProduct(id);
		if (p.isPresent()) {
			// Get the received product
			Product currentProduct = p.get();
			String code = product.getCode();
			if (code != null) {
				currentProduct.setCode(code);
			}
			String name = product.getName();
			if (name != null) {
				currentProduct.setName(name);
			}
			String description = product.getDescription();
			if (description != null) {
				currentProduct.setDescription(description);
			}
			Float price = product.getPrice();
			if (description != null) {
				currentProduct.setPrice(price);
			}
			Integer quantity = product.getQuantity();
			if (quantity != null) {
				currentProduct.setQuantity(quantity);
			}
			String inventoryStatus = product.getInventoryStatus();
			if (inventoryStatus != null) {
				currentProduct.setInventoryStatus(inventoryStatus);
			}
			String category = product.getCategory();
			if (category != null) {
				currentProduct.setCategory(category);
			}
			String image = product.getImage();
			if (image != null) {
				currentProduct.setImage(image);
			}
			Integer rating = product.getRating();
			if (rating != null) {
				currentProduct.setRating(rating);
			}
			// Save the product with the new properties
			Product productSaved = productService.saveProduct(currentProduct);
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
			return ResponseEntity.ok("Product with id " + id + "deleted successfully!.");
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
