package org.micropos.products.repository;

import org.micropos.products.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    public Flux<String> all();

    public Mono<Product> get(String id);

    public Mono<Product> create(Product item);

    public Mono<Product> update(Product item);

    public Mono<Product> remove(String id);
}
