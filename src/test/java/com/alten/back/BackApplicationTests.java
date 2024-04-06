package com.alten.back;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.alten.back.api.BackApplication;
import com.alten.back.api.model.Product;
import com.alten.back.api.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = BackApplication.class)
//@RunWith(SpringRunner.class)
//@JdbcTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BackApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductService productService;
	
	@Autowired
	ObjectMapper objectMapper;

	// Unit test
	// Ont pour vocation à tester uniquement le contenu d’une méthode
	@Test
	public void testGetProducts() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk());
	}

	// Test integration
	// Ont pour vocation de tester plus largement une fonctionnalité
	@Test
	public void testGetProduct() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("Bamboo Watch")));
	}

//	@Test
//	public void testCreateProduct() throws Exception {
//		String json = "{" + "\"id\":1," + "\"code\":\"code1\"," + "\"name\":\"name1\"" + "}";
//		this.mockMvc.perform(post("/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(json)))
//                .andExpect(status().isCreated());
//	}

}
