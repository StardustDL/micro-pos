package org.micropos.carts.service;

import org.micropos.carts.exception.ProductNotFoundException;
import org.micropos.carts.model.Cart;
import org.micropos.carts.model.Item;
import org.micropos.carts.model.Product;
import org.micropos.carts.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Mono<Cart> addItem(String cartId, Item item) {
        return cartRepository.get(cartId).flatMap(cart -> {
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
                    try {
                        Product product = productService.get(item.getProductId());
                        if (product != null) {
                            items.add(item.withProductId(product.getId()));
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        return Mono.error(new ProductNotFoundException());
                    }
                }
            }
            return cartRepository.update(cart);
        });
    }
}
