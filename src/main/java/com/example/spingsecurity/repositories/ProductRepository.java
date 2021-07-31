package com.example.spingsecurity.repositories;

import com.example.spingsecurity.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
