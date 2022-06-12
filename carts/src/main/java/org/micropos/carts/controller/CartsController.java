package org.micropos.carts.controller;

import java.util.Collection;
import java.util.List;

import org.micropos.carts.exception.ProductNotFoundException;
import org.micropos.carts.model.Cart;
import org.micropos.carts.model.Item;
import org.micropos.carts.repository.CartRepository;
import org.micropos.carts.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class CartsController {
    @Autowired
    private CartRepository repository;

    @Autowired
    private CartService service;

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> all() {
        return repository.all();
    }

    @GetMapping("/full")
    public Mono<List<String>> allFull() {
        return repository.all().collectList();
    }

    @GetMapping("/{id}")
    public Mono<Cart> get(@PathVariable String id) {
        return repository.get(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Cart> delete(@PathVariable String id) {
        return repository.remove(id);
    }

    @PostMapping(value = "/{id}")
    public Mono<Cart> update(@PathVariable("id") String id, @RequestBody Item item) throws ProductNotFoundException {
        return service.addItem(id, item);
    }
}