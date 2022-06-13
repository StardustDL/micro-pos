package org.micropos.orders.service;

import org.micropos.orders.model.Order;
import org.micropos.orders.model.OrderRequest;

import reactor.core.publisher.Mono;

public interface OrderService {
    public Mono<Order> create(OrderRequest item);
}
