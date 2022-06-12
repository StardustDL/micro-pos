package org.micropos.products.repository;

import java.util.UUID;

import org.micropos.products.db.ProductDb;
import org.micropos.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private ProductDb db;

    @Override
    public Flux<String> all() {
        return db.findAll().map(Product::getId);
    }

    @Override
    public Mono<Product> get(String id) {
        return db.findById(id);
    }

    @Override
    public Mono<String> create(Product item) {
        return db.save(item.withId(UUID.randomUUID().toString())).map(Product::getId);
    }

    @Override
    public Mono<Void> update(Product item) {
        return Mono.just(item).filterWhen(x -> db.existsById(x.getId())).flatMap(x -> db.save(x)).then();
    }

    @Override
    public Mono<Void> remove(String id) {
        return db.deleteById(id);
    }
}
