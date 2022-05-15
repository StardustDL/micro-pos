package org.micropos.products.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.micropos.products.db.ProductDb;
import org.micropos.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultProductRepository implements ProductRepository {

    @Autowired
    private ProductDb db;

    @Override
    public Collection<String> all() {
        List<String> ids = new ArrayList<>();
        for (Product product : db.findAll()) {
            ids.add(product.getId());
        }
        return ids;
    }

    @Override
    public Optional<Product> get(String id) {
        return db.findById(id);
    }

    @Override
    public String create() {
        Product item = new Product();
        item.setId(UUID.randomUUID().toString());
        db.save(item);
        return item.getId();
    }

    @Override
    public void update(Product item) {
        if (db.existsById(item.getId())) {
            db.save(item);
        }
    }

    @Override
    public void remove(String id) {
        db.deleteById(id);
    }
}
