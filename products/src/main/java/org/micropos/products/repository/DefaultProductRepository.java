package org.micropos.products.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.micropos.products.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultProductRepository implements ProductRepository {
    @Override
    public Collection<String> all() {
        return new ArrayList<String>();
    }

    @Override
    public Optional<Product> get(String id) {
        return Optional.ofNullable(id.equals("1") ? new Product() : null);
    }
}
