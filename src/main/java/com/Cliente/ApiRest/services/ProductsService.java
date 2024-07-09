package com.Cliente.ApiRest.services;

import com.Cliente.ApiRest.entities.Product;
import com.Cliente.ApiRest.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired ProductsRepository repository;

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public List<Product> readProducts(){
        return repository.findAll();
    }

    public Optional<Product> readOneProduct(Long id){
        return repository.findById(id);
    }

    public Optional<Product> updateProduct(Long id, Product newData){
        return repository.findById(id).map(product -> {
           product.setDescription(newData.getDescription());
           product.setCode(newData.getCode());
           product.setStock(newData.getStock());
           product.setPrice(newData.getPrice());
           return repository.save(product);
        });
    }

}
