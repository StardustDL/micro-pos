package org.micropos.carts.repository;

import java.util.Collection;
import java.util.Optional;

import org.micropos.carts.model.Cart;

public interface CartRepository {
    public Collection<String> all();

    public Optional<Cart> get(String id);

    public String create();

    public void update(Cart cart);

    public void remove(String id);
}
