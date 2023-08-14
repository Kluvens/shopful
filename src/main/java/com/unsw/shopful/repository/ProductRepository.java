package com.unsw.shopful.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unsw.shopful.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    public Optional<Product> findById(String id);
    
}
