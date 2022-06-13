package org.micropos.delivery.db;

import org.micropos.delivery.model.Delivery;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DeliveryDb extends ReactiveMongoRepository<Delivery, String> {
}
