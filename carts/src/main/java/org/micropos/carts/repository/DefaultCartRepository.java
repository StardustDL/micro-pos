package org.micropos.carts.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.micropos.carts.model.Cart;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultCartRepository implements CartRepository {
    @Override
    public Collection<String> all() {
        return new ArrayList<String>();
    }

    @Override
    public Optional<Cart> get(String id) {
        return Optional.ofNullable(id.equals("1") ? new Cart() : null);
    }

    @Override
    public String create() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Cart cart) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(String id) {
        // TODO Auto-generated method stub
        
    }
}
