package com.unsw.shopful.repository;

import java.util.Optional;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.unsw.shopful.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    public Optional<Product> findById(String id);

    @Query("{'id': ?0}")
    public Product partialUpdate(String productId, Map<String, Object> updates);
}
