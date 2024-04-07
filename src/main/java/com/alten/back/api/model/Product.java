package com.alten.back.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
