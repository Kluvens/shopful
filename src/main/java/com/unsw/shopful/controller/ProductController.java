package com.unsw.shopful.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.exception.NotFoundException;
import com.unsw.shopful.model.Product;
import com.unsw.shopful.request.CreateProductRequest;
import com.unsw.shopful.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    private static final Logger logger = ShopfulApplication.logger;

    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequest createProductRequest) {
        String productName = createProductRequest.getProductName();

        Product newProduct = productService.createProduct(productName);
        logger.info("Created {}", newProduct);

        return newProduct;
    }

    @GetMapping
    public List<Product> getProducts() {
        logger.info("Get all products ...");

        return productService.getAllProducts();
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<Product> patchProduct(
    //         @PathVariable String id, 
    //         @RequestBody Map<String, Object> fieldUpdates) {
    //     try {
    //         productService.updateProductFields(id, fieldUpdates);
    //     } catch (NotFoundException e) {
    //         log.warn("Product to be updated not found");
    //         return ResponseEntity.notFound().build();
    //     } catch (Exception e) {
    //         log.error("Exception, ", e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        try {
            Product deletedProduct = productService.deleteProduct(id);
            return ResponseEntity.ok(deletedProduct);
        } catch (NotFoundException e) {
            logger.error("Product to be deleted not found.");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Exception, " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
