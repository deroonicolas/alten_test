package com.alten.back.api.controller;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alten.back.api.model.Product;
import com.alten.back.api.service.ProductService;
import com.alten.backi.api.exception.ProductNotFoundException;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * Create - Add a new product
	 * 
	 * @param product A product object
	 * @return The product object saved
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Validated @RequestBody final Product product) {
		final Product productSaved = productService.saveProduct(product);
		if (Objects.isNull(productSaved)) {
			return ResponseEntity.noContent().build();
		}
		final URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(productSaved.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Read - Get one product
	 * 
	 * @param id The id of the product
	 * @return An Product object full filled
	 */
	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable("id") final Long id) {
		final Optional<Product> product = productService.getProduct(id);
		if (product.isPresent()) {
			return product.get();
		} else {
			throw new ProductNotFoundException("Le produit avec l'id " + id + " est introuvable.");
		}
	}

	/**
	 * Read - Get all products
	 * 
	 * @return - An Iterable object of Product full filled
	 */
	@GetMapping("/products")
	public Iterable<Product> getProducts() {
		return productService.getProducts();
	}

	/**
	 * Update - Update an existing product
	 * 
	 * @param id       - The id of the product to update
	 * @param employee - The product object to update
	 * @return The product object updated
	 */
	@PatchMapping("/products/{id}")
	public Product updateEmployee(@PathVariable("id") final Long id, @RequestBody final Product product) {
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
			productService.saveProduct(currentProduct);
			return currentProduct;
		} else {
			throw new ProductNotFoundException("Le produit avec l'id " + id + " est introubale.");
		}
	}

	/**
	 * Delete - Delete a product
	 * 
	 * @param id - The id of the product to delete
	 */
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable("id") final Long id) {
		final Optional<Product> p = productService.getProduct(id);
		if (p.isPresent()) {
			productService.deleteProduct(id);
		} else {
			throw new ProductNotFoundException("Le produit avec l'id " + id + " est INTROUVABLE.");
		}
	}
	
}
