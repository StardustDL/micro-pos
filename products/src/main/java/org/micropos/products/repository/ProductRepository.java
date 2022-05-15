package org.micropos.products.repository;

import java.util.Collection;
import java.util.Optional;

import org.micropos.products.model.Product;

public interface ProductRepository {
    public Collection<String> all();

    public Optional<Product> get(String id);

    public String create();

    public void update(Product item);

    public void remove(String id);
}
