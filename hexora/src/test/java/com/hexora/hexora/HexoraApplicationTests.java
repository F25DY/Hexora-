package com.hexora.hexora;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "hexora.network.discovery-enabled=false")
class HexoraApplicationTests {

	@Test
	void contextLoads() {
	}

}
