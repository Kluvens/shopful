package com.unsw.shopful.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.exception.NotFoundException;
import com.unsw.shopful.mapper.ProductMapper;
import com.unsw.shopful.model.Product;
import com.unsw.shopful.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger logger = ShopfulApplication.logger;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @CacheEvict(cacheNames = "products", allEntries = true)
    public Product createProduct(String productName) {

        logger.info("Creating a product ...");

        Product newProduct = new Product()
            .setProductName(productName);

        return productRepository.save(newProduct);
    }

    @Cacheable(cacheNames = "products")
    public List<Product> getAllProducts() {

        logger.info("Getting all products ...");

        return productRepository.findAll();
    }

    @CacheEvict(cacheNames = "products", allEntries = true)
    public Product deleteProduct(String id) throws Exception {

        logger.info("Deleting a product ...");
        
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.delete(product.get());
            return product.get();
        }
        throw new NotFoundException("Product not found");
    }

    public Product incrementSavedCount(String productId) {
        Product product = productRepository.findById(productId).get();
        int savedCount = product.getSavedCount();
        product.setSavedCount(savedCount++);
        productRepository.save(product);
        return product;
    }

    public Product decrementSavedCount(String productId) {
        Product product = productRepository.findById(productId).get();
        int savedCount = product.getSavedCount();
        product.setSavedCount(savedCount--);
        productRepository.save(product);
        return product;
    }

    public Optional<Product> findById(String productId) {
        return productRepository.findById(productId);
    }

    // @CacheEvict(cacheNames = "products", allEntries = true)
    // public Product updateProduct(String id, Map<String, Object> fieldsUpdate) throws Exception {
    //     Product product = productRepository.findById(id)
    //         .orElseThrow(() -> new NotFoundException("Product to be updated not found"));

    //     for (Map.Entry<String, Object> entry : fieldsUpdate.entrySet()) {
    //         String field = entry.getKey();
    //         Object value = entry.getValue();

    //         product.getAttributes().put(field, value);
    //     }

    //     return productRepository.save(product);
    // }
}
