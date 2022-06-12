package org.micropos.orders.db;

import org.micropos.orders.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderDb extends ReactiveMongoRepository<Order, String> {
}
