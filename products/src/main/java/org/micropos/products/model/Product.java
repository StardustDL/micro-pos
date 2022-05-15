package org.micropos.products.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    private String id;

    private String name;

    private double price;

    private String image;
}
