package org.micropos.products.db;

import org.micropos.products.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDb extends CrudRepository<Product, String> {

}
