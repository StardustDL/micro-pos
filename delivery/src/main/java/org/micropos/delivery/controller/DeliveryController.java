package org.micropos.delivery.controller;

import java.util.Collection;
import java.util.List;

import org.micropos.delivery.exception.DeliveryNotFoundException;
import org.micropos.delivery.exception.OrderNotFoundException;
import org.micropos.delivery.model.Order;
import org.micropos.delivery.model.Delivery;
import org.micropos.delivery.model.Item;
import org.micropos.delivery.repository.DeliveryRepository;
import org.micropos.delivery.service.OrderService;
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

@RestController
@RequestMapping("/api")
public class DeliveryController {
    @Autowired
    private DeliveryRepository repository;

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> all() {
        return repository.all();
    }

    @GetMapping("/full")
    public Mono<List<String>> allFull() {
        return repository.all().collectList();
    }

    @GetMapping("/{id}")
    public Mono<Delivery> get(@PathVariable String id) throws DeliveryNotFoundException {
        return repository.get(id).switchIfEmpty(Mono.error(new DeliveryNotFoundException()));
    }

    @DeleteMapping("/{id}")
    public Mono<Delivery> delete(@PathVariable String id) {
        return repository.remove(id);
    }
}