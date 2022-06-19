package com.ironhack.edgeservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EdgeServiceApplicationTests {

	@Autowired
	private EdgeServiceApplication edgeServiceApplication;

	@Test
	void contextLoads() {
		assertNotNull(edgeServiceApplication);
	}

	@Test
	void main() {
		EdgeServiceApplication.main(new String[] {});
	}

}
