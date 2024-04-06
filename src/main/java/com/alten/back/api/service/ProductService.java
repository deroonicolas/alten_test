package com.alten.back.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.back.api.model.Product;
import com.alten.back.api.repository.ProductRepository;

import lombok.Data;

@Data
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Gets the product with the specified id
     * @param id The id of the product
     * @return The product object if exists
     */
    public Optional<Product> getProduct(final Long id) {
        return productRepository.findById(id);
    }

    /**
     * Gets all products
     * @return All product objects
     */
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Deletes a product with the specified id
     * @param id Id of the product to delete
     */
    public void deleteProduct(final Long id) {
    	productRepository.deleteById(id);
    }

    /**
     * Saves a product
     * @param product The product object
     * @return The saved product
     */
    public Product saveProduct(final Product product) {
    	final Product savedProduct = productRepository.save(product);
        return savedProduct;
    }
    
    /**
     * Saves a list of products
     * @param products The list of products
     */
	public void saveProducts(final List<Product> products) {
		products.forEach(product -> this.saveProduct(product));
	}

}
