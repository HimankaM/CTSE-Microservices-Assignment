package com.example.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productservice.model.Product;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {

        return List.of(
            new Product(1L, "Laptop", 2500),
            new Product(2L, "Phone", 1200)
        )
        .stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}