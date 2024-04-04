package com.alten.back.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alten.back.api.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
