package org.micropos.carts.service;

import org.micropos.carts.model.Cart;
import org.micropos.carts.model.Item;

import reactor.core.publisher.Mono;

public interface CartService {
    public Mono<Cart> addItem(String cartId, Item item);
}
