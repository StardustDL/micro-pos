package org.micropos.products.repository;

import org.micropos.products.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    public Flux<String> all();

    public Mono<Product> get(String id);

    public Mono<String> create(Product item);

    public Mono<Void> update(Product item);

    public Mono<Void> remove(String id);
}
