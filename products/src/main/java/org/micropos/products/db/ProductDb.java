package org.micropos.products.db;

import org.micropos.products.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDb extends MongoRepository<Product, String> {

}
