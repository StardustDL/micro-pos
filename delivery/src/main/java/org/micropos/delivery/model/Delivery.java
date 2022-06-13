package org.micropos.delivery.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Document(collection = "delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Delivery {
    @Id
    private String id;

    private Order order;
}
