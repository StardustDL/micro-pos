package org.micropos.products.db;

import org.micropos.products.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface ProductDb extends ReactiveMongoRepository<Product, String> {
}
