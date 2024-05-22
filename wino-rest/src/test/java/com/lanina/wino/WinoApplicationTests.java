package com.lanina.wino;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Context Load Test")
@Import(MockPhotoServiceConfiguration.class)
class WinoApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		assertNotNull(context.getApplicationName());
	}

	@Test
	void camLoadMainTest() {
		assertDoesNotThrow(() -> WinoApplication.main(new String[] {}));
	}

}
