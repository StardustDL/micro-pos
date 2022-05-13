package com.micropos.products.mapper;

import com.micropos.products.dto.ProductDto;
import com.micropos.products.model.Product;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface ProductMapper {

    Collection<ProductDto> toProductsDto(Collection<Product> products);

    Collection<Product> toProducts(Collection<ProductDto> products);

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product pet);
}
