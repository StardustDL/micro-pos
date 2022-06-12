package org.micropos.carts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@Data
@AllArgsConstructor
@With
public class Item {
    private final String productId;

    private int quantity;
}
