package com.Cliente.ApiRest.repositories;

import com.Cliente.ApiRest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
}
