package org.micropos.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;

@Configuration
public class DeliveryIntegration {
    @Bean
    public DirectChannel deliveryRequestChannel() {
        return new DirectChannel();
    }
}

@Component
class HttpInboundGateway {
    @Bean
    public IntegrationFlow inGate() {
        return IntegrationFlows
                .from(WebFlux.inboundChannelAdapter("/integration/delivery/{id}")
                        .requestMapping(m -> m.methods(HttpMethod.GET))
                        .payloadExpression("#pathVariables.id")
                        .statusCodeFunction(m -> HttpStatus.ACCEPTED))
                .channel("deliveryRequestChannel")
                .get();
    }
}

@Component
class HttpOutboundGateway {
    @Bean
    public IntegrationFlow outGate() {
        return IntegrationFlows.from("deliveryRequestChannel")
                .handle(WebFlux
                        .<String>outboundGateway(
                                m -> UriComponentsBuilder
                                        .fromUriString("http://localhost:10900/api/"
                                                + m.getPayload())
                                        .build()
                                        .toUri())
                        .httpMethod(HttpMethod.GET))
                .handle(System.out::println)
                .get();
    }
}
