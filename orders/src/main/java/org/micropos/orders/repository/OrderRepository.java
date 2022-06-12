package org.micropos.orders.repository;

import org.micropos.orders.model.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    public Flux<String> all();

    public Mono<Order> get(String id);

    public Mono<Order> create(Order item);

    public Mono<Order> update(Order item);

    public Mono<Order> remove(String id);
}
