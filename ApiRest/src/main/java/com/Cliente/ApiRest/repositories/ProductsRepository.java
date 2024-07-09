package com.Cliente.ApiRest.repositories;

import com.Cliente.ApiRest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
