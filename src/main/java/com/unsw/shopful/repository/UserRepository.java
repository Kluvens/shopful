package com.unsw.shopful.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unsw.shopful.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {
    public User findByEmail(String email);

    public User findByUsername(String username);

    public Optional<User> findById(String id);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);
}
