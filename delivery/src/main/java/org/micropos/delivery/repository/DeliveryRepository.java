package org.micropos.delivery.repository;

import org.micropos.delivery.model.Delivery;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryRepository {
    public Flux<String> all();

    public Mono<Delivery> get(String id);

    public Mono<Delivery> create(Delivery item);

    public Mono<Delivery> update(Delivery item);

    public Mono<Delivery> remove(String id);
}
