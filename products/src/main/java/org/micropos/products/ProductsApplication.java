package org.micropos.products;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${project.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("MicroPos Products API").version(appVersion)
						.license(new License().name("MPL 2.0").url("https://github.com/StardustDL/micro-pos")));
	}
}
