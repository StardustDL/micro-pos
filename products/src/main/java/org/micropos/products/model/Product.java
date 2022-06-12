package org.micropos.products.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Product {
    @Id
    private String id;

    @Indexed
    private String name;

    @Indexed
    private String description;

    private String image;

    private double price;
}
