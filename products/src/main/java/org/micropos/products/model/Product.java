package org.micropos.products.model;

import lombok.Data;

@Data
public class Product {
    private String id;

    private String name;

    private double price;

    private String image;
}
