package org.micropos.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(GatewayApplication.class, args);
		context.getBean(DiscoveryClient.class).getServices().forEach(System.out::println);
	}
}
