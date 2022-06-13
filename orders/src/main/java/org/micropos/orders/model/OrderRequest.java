package org.micropos.orders.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class OrderRequest {
    private String id;

    private List<ItemRequest> items = new ArrayList<>();
}
