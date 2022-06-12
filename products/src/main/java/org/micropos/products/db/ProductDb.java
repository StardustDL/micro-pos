package org.micropos.products.db;

import org.micropos.products.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductDb extends ReactiveMongoRepository<Product, String> {
}
