package com.kozubaev.ayu.osago.project.service;

import com.kozubaev.ayu.osago.project.model.Product;
import com.kozubaev.ayu.osago.project.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
