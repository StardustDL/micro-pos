package org.micropos.delivery.repository;

import java.util.UUID;

import org.micropos.delivery.db.DeliveryDb;
import org.micropos.delivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository {

    @Autowired
    private DeliveryDb db;

    @Override
    public Flux<String> all() {
        return db.findAll().map(Delivery::getId);
    }

    @Override
    public Mono<Delivery> create(Delivery item) {
        return db.save(item);
    }

    @Override
    public Mono<Delivery> get(String id) {
        return db.findById(id);
    }

    @Override
    public Mono<Delivery> update(Delivery item) {
        return Mono.just(item).filterWhen(x -> db.existsById(x.getId())).flatMap(x -> db.save(x));
    }

    @Override
    public Mono<Delivery> remove(String id) {
        return db.existsById(id).flatMap(has -> {
            if (has) {
                return get(id).flatMap(item -> db.deleteById(id).thenReturn(item));
            }
            return Mono.empty();
        });
    }
}
