package org.micropos.carts.repository;

import org.micropos.carts.model.Cart;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartRepository {
    public Flux<String> all();

    public Mono<Cart> get(String id);

    public Mono<Cart> update(Cart item);

    public Mono<Cart> remove(String id);
}
