package com.alten.back.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @SequenceGenerator(name = "product_seq", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_seq")
	private Long id;

	@NotBlank(message = "Code is mandatory")
	private String code;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotBlank(message = "Description is mandatory")
	private String description;

	@PositiveOrZero(message = "Price has to be zero or more")
	private float price;

	@PositiveOrZero(message = "Inventory has to be zero or more")
	private int quantity;

	@NotBlank(message = "Inventory is mandatory")
	@Column(name="inventory_status")
	private String inventoryStatus;

	@NotBlank(message = "Category is mandatory")
	private String category;

	@NotBlank(message = "Image is mandatory")
	private String image;

	@Positive(message = "Rating has to be positive")
	private int rating;

}
