package org.micropos.products.repository;

import java.util.Collection;
import java.util.Optional;

import org.micropos.products.model.Product;

public interface ProductRepository {
    public Collection<Product> all();

    public Optional<Product> findById(String id);
}
