package org.micropos.carts.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Cart {
    private String id;

    private List<Item> items = new ArrayList<>();
}
