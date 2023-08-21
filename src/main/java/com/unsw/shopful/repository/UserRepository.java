package com.unsw.shopful.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unsw.shopful.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByEmail(String email);

    public User findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);
}
