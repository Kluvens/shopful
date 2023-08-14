package com.unsw.shopful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.unsw.shopful.dto.ProductDTO;
import com.unsw.shopful.mapper.ProductMapper;
import com.unsw.shopful.model.Product;
import com.unsw.shopful.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @CacheEvict(cacheNames = "products", allEntries = true)
    public Product createProduct(String productName) {
        Product newProduct = new Product()
            .setProductName(productName);

        return productRepository.save(newProduct);
    }

    @Cacheable(cacheNames = "products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @CacheEvict(cacheNames = "products", allEntries = true)
    public Product deleteProduct(String id) throws Exception {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.delete(product.get());
            return product.get();
        }
        throw new Exception("Product not found");
    }
}
