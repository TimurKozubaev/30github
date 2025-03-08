package com.kozubaev.ayu.osago.project.repository;

import com.kozubaev.ayu.osago.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
