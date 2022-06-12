package org.micropos.carts.repository;

import java.util.UUID;

import org.micropos.carts.db.CartDb;
import org.micropos.carts.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CartRepositoryImpl implements CartRepository {

    @Autowired
    private CartDb db;

    @Override
    public Flux<String> all() {
        return db.findAll().map(Cart::getId);
    }

    @Override
    public Mono<Cart> get(String id) {
        return db.findById(id).switchIfEmpty(db.save(new Cart().withId(id)));
    }

    @Override
    public Mono<Cart> update(Cart item) {
        return Mono.just(item).filterWhen(x -> db.existsById(x.getId())).flatMap(x -> db.save(x));
    }

    @Override
    public Mono<Cart> remove(String id) {
        return db.existsById(id).flatMap(has -> {
            if (has) {
                return get(id).flatMap(item -> db.deleteById(id).thenReturn(item));
            }
            return Mono.empty();
        });
    }
}
