package org.micropos.orders.controller;

import java.util.Collection;
import java.util.List;

import org.micropos.orders.exception.OrderNotFoundException;
import org.micropos.orders.exception.ProductNotFoundException;
import org.micropos.orders.model.Order;
import org.micropos.orders.model.OrderRequest;
import org.micropos.orders.model.Item;
import org.micropos.orders.repository.OrderRepository;
import org.micropos.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class OrdersController {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> all() {
        return repository.all();
    }

    @GetMapping("/full")
    public Mono<List<String>> allFull() {
        return repository.all().collectList();
    }

    @GetMapping("/{id}")
    public Mono<Order> get(@PathVariable String id) throws OrderNotFoundException {
        return repository.get(id).switchIfEmpty(Mono.error(new OrderNotFoundException()));
    }

    @DeleteMapping("/{id}")
    public Mono<Order> delete(@PathVariable String id) {
        return repository.remove(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Mono<Order> create(@RequestBody OrderRequest item) throws ProductNotFoundException {
        return service.create(item);
    }
}