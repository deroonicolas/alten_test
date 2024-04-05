package com.alten.back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.alten.back.api.BackApplication;

@SpringBootTest
@ContextConfiguration(classes = BackApplication.class)
class BackApplicationTests {

	@Test
	void contextLoads() {
	}

}
