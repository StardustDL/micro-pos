package org.micropos.carts.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Document(collection = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Cart {
    @Id
    private String id;

    private List<Item> items = new ArrayList<>();
}
