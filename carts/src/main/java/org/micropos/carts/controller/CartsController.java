package org.micropos.carts.controller;

import java.util.Collection;

import org.micropos.carts.exception.CartNotFoundException;
import org.micropos.carts.model.Cart;
import org.micropos.carts.repository.CartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartsController {
    @Autowired
    private CartRepository repository;

    @GetMapping("")
    public Collection<String> all() {
        return repository.all();
    }

    @GetMapping("/{id}")
    public Cart get(@PathVariable("id") String id) throws CartNotFoundException {
        return repository.get(id).orElseThrow(() -> new CartNotFoundException());
    }
}
