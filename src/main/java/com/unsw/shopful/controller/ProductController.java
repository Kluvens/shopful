package com.unsw.shopful.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.model.Product;
import com.unsw.shopful.request.CreateProductRequest;
import com.unsw.shopful.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final Logger log = ShopfulApplication.log;

    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequest createProductRequest) {
        String productName = createProductRequest.getProductName();

        return productService.createProduct(productName);
    }

    @GetMapping
    public List<Product> getProducts() {
        log.info("get all products");
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        try {
            Product deletedProduct = productService.deleteProduct(id);
            return ResponseEntity.ok(deletedProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
