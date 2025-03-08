package com.kozubaev.ayu.osago.project.controller;

import com.kozubaev.ayu.osago.project.model.Product;
import com.kozubaev.ayu.osago.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/user/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> getProduct=productService.findAll();
        return ResponseEntity.ok(getProduct);
    }
}
