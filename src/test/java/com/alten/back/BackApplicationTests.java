package com.alten.back;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.alten.back.api.BackApplication;


@AutoConfigureMockMvc
@Transactional
@SpringBootTest(classes = BackApplication.class)
class BackApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test for all products retrieve
	 * @throws Exception
	 */
	@Test
	public void testGetProducts() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk());
	}

	/**
	 * Test for single product retrieve
	 * @throws Exception
	 */
	@Test
	public void testGetProduct() throws Exception {
		mockMvc.perform(get("/products/1000")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Bamboo Watch")));
	}

	/**
	 * Test for product creation
	 * @throws Exception
	 */
	@Test
	public void testCreateProduct() throws Exception {
		mockMvc.perform(post("/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{"id" : 1, "code" : "code1", "name" : "name1", "description": "desc1",
				"price": 1, "quantity": 1, "inventoryStatus": "INSTOCK", "category": "Fitness",
				"image": "yoga-mat.jpg", "rating": 1}
				"""))
			.andExpect(status().isCreated());
	}

	@Test
	public void testPatchProduct() throws Exception {
		mockMvc.perform(patch("/products/1000")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{"name" : "nameModified", "description": "descriptionModified"}
				"""))
			.andExpect(status().isOk());
		mockMvc.perform(get("/products/1000")).andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("nameModified")))
			.andExpect(jsonPath("$.description", is("descriptionModified")));
	}

	/**
	 * Test for product deletion
	 * @throws Exception
	 */
	@Test
	public void testDeleteProduct() throws Exception {
		mockMvc.perform(delete("/products/1000"))
			.andExpect(status().isOk());
	}

}
