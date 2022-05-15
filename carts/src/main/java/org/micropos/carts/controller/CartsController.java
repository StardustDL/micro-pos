package org.micropos.carts.controller;

import java.util.Collection;

import org.micropos.carts.exception.CartNotFoundException;
import org.micropos.carts.model.Cart;
import org.micropos.carts.model.Item;
import org.micropos.carts.repository.CartRepository;
import org.micropos.carts.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/carts")
public class CartsController {
    @Autowired
    private CartService service;

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

    @PostMapping("/{id}")
    public void addItem(@PathVariable("id") String id, @RequestBody Item item) throws CartNotFoundException {
        service.addItem(id, item);
    }

    @DeleteMapping("/{id}/items/{productId}")
    public void removeItem(@PathVariable("id") String id, @PathVariable("productId") String productId)
            throws CartNotFoundException {
        service.removeItem(id, productId);
    }

    @PutMapping("/{id}")
    public void checkout(@PathVariable("id") String id) throws CartNotFoundException {
        service.checkout(id);
    }
}
