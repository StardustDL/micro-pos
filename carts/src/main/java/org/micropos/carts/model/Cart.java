package org.micropos.carts.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@SessionScope
@Component
@Data
public class Cart {
    private String id = UUID.randomUUID().toString();

    private List<Item> items = new ArrayList<>();
}
