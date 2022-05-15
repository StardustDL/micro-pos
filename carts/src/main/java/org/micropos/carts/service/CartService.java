package org.micropos.carts.service;

import org.micropos.carts.model.Item;

public interface CartService {
    public void addItem(String cartId, Item item);

    public void removeItem(String cartId, String productId);

    public void checkout(String cartId);
}
