package org.micropos.orders.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Order {
    @Id
    private String id;

    private List<Item> items = new ArrayList<>();
}
