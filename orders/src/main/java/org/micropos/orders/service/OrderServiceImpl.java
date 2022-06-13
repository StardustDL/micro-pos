package org.micropos.orders.service;

import org.micropos.orders.model.Order;
import org.micropos.orders.model.OrderRequest;
import org.micropos.orders.repository.OrderRepository;
import org.micropos.orders.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApplicationContext context;

    @Override
    public Mono<Order> create(OrderRequest item) {
        return Flux.fromIterable(item.getItems())
                .filter(x -> x.getQuantity() > 0)
                .map(x -> new Item(productService.get(x.getProductId()), x.getQuantity()))
                .filter(x -> x.getProduct() != null)
                .collectList()
                .map(x -> new Order(item.getId(), x))
                .flatMap(x -> repository.create(x))
                .map(x -> {
                    // TODO: send event
                    return x;
                });
    }
}