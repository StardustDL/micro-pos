package org.micropos.products.controller;

import java.util.Collection;

import org.micropos.products.exception.ProductNotFoundException;
import org.micropos.products.model.Product;
import org.micropos.products.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
public class ProductsController {
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    public Collection<String> all() {
        return repository.all();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable("id") String id) throws ProductNotFoundException {
        return repository.get(id).orElseThrow(() -> new ProductNotFoundException());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.remove(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public String create(@RequestBody Product item) {
        return repository.create(item);
    }

    @PutMapping(value="/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Product item) {
        repository.update(item.withId(id));
    }
}
