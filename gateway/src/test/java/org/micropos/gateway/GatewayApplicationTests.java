package org.micropos.gateway;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private String url(String endpoint) {
		return "http://localhost:" + this.port + endpoint;
	}

	@Test
	public void shouldStartEurekaServer() {
		ResponseEntity<String> entity = this.testRestTemplate.getForEntity(this.url("/eureka/apps"), String.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
