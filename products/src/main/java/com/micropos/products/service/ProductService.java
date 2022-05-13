package com.micropos.products.service;

import com.micropos.products.model.Product;

import java.util.List;

public interface ProductService {


    public List<Product> products();

    public Product getProduct(String id);

    public Product randomProduct();
}
