package org.micropos.carts.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.micropos.carts.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultCartRepository implements CartRepository {
    @Autowired
    private Cart raw;

    @Override
    public Collection<String> all() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add(raw.getId());
        return ret;
    }

    @Override
    public Optional<Cart> get(String id) {
        return Optional.ofNullable(id.equals(raw.getId()) ? raw : null);
    }

    @Override
    public String create() {
        raw.setId(UUID.randomUUID().toString());
        raw.setItems(new ArrayList<>());
        return raw.getId();
    }

    @Override
    public void update(Cart cart) {
        if (cart.getId().equals(raw.getId())) {
            raw.setItems(cart.getItems());
        }
    }

    @Override
    public void remove(String id) {
        if (id.equals(raw.getId())) {
            create();
        }
    }
}
