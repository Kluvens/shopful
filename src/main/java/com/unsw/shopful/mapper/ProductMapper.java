package com.unsw.shopful.mapper;

import org.springframework.stereotype.Component;

import com.unsw.shopful.dto.ProductDTO;
import com.unsw.shopful.model.Product;

@Component
public class ProductMapper {
    
    public ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO()
            .setId(product.getId())
            .setProductName(product.getProductName());

        return dto;
    }
}
