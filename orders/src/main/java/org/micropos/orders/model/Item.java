package org.micropos.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@Data
@AllArgsConstructor
@With
public class Item {
    private final Product product;

    private int quantity;
}
