package org.micropos.carts.service;

import java.util.ArrayList;

import org.micropos.carts.exception.CartNotFoundException;
import org.micropos.carts.model.Cart;
import org.micropos.carts.model.Item;
import org.micropos.carts.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultCartService implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addItem(String cartId, Item item) {
        Cart cart = cartRepository.get(cartId).orElseThrow(() -> new CartNotFoundException());
        var items = cart.getItems();
        var target = items.stream().filter(i -> i.getProductId().equals(item.getProductId())).findFirst();
        if (target.isPresent()) {
            var quan = target.get().getQuantity() + item.getQuantity();
            if (quan > 0) {
                target.get().setQuantity(quan);
            } else {
                items.remove(target.get());
                cart.setItems(items);
            }
        } else {
            if (item.getQuantity() > 0) {
                RestTemplate restTemplate = new RestTemplate();
                try {
                    restTemplate.getForEntity("http://localhost:9001/api/products/" + item.getProductId(),
                            Object.class);
                } catch (Exception e) {
                    
                }
                items.add(item);
            }
        }

        cartRepository.update(cart);
    }

    @Override
    public void removeItem(String cartId, String productId) {
        Cart cart = cartRepository.get(cartId).orElseThrow(() -> new CartNotFoundException());
        var items = cart.getItems();
        var item = items.stream().filter(i -> i.getProductId().equals(productId)).findFirst();
        if (item.isPresent()) {
            items.remove(item.get());
        }

        cartRepository.update(cart);
    }

    @Override
    public void checkout(String cartId) {
        Cart cart = cartRepository.get(cartId).orElseThrow(() -> new CartNotFoundException());
        cart.setItems(new ArrayList<>());

        System.out.println("Checkout cart: " + cart);

        cartRepository.update(cart);
    }

}
