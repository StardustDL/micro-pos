package org.micropos.delivery;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@RemoteApplicationEventScan
public class DeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${project.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("MicroPos Delivery API").version(appVersion)
						.license(new License().name("MPL 2.0").url("https://github.com/StardustDL/micro-pos")));
	}
}
