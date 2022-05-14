package org.micropos.products.controller;

import java.util.Collection;

import org.micropos.products.exception.ProductNotFoundException;
import org.micropos.products.model.Product;
import org.micropos.products.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    public Collection<Product> all() {
        return repository.all();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") String id) throws ProductNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }
}
