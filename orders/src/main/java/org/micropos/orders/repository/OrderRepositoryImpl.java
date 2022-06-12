package org.micropos.orders.repository;

import java.util.UUID;

import org.micropos.orders.db.OrderDb;
import org.micropos.orders.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderDb db;

    @Override
    public Flux<String> all() {
        return db.findAll().map(Order::getId);
    }

    @Override
    public Mono<Order> create(Order item) {
        return db.save(item.withId(UUID.randomUUID().toString()));
    }

    @Override
    public Mono<Order> get(String id) {
        return db.findById(id).switchIfEmpty(db.save(new Order().withId(id)));
    }

    @Override
    public Mono<Order> update(Order item) {
        return Mono.just(item).filterWhen(x -> db.existsById(x.getId())).flatMap(x -> db.save(x));
    }

    @Override
    public Mono<Order> remove(String id) {
        return db.existsById(id).flatMap(has -> {
            if (has) {
                return get(id).flatMap(item -> db.deleteById(id).thenReturn(item));
            }
            return Mono.empty();
        });
    }
}
