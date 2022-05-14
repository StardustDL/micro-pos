package org.micropos.carts.service;

public interface CartService {
    public void addItem(String cartId, String productId, int quantity);

    public void removeItem(String cartId, String productId);

    public void clear(String cartId);

    public void checkout(String cartId);
}
