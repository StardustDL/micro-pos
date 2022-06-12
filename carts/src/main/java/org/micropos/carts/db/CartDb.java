package org.micropos.carts.db;

import org.micropos.carts.model.Cart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CartDb extends ReactiveMongoRepository<Cart, String> {
}
