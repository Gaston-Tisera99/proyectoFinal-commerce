package com.Cliente.ApiRest.controllers;

import com.Cliente.ApiRest.entities.Product;
import com.Cliente.ApiRest.services.ClientsService;
import com.Cliente.ApiRest.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductsController {
    @Autowired private ProductsService service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product data){
        try{
            Product product = service.saveProduct(data);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
   public ResponseEntity<List<Product>> readProducts(){
        try{
            List<Product> products = service.readProducts();
            return ResponseEntity.ok(products);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{pid}")
    public Optional<Product> readOneProduct(@PathVariable("id") Long id){
        try{
            return service.readOneProduct(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Read One Error");
        }
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product newData){
        try{
            Optional<Product> updateProduct = service.updateProduct(id, newData);

            if(updateProduct.isPresent()){
                return ResponseEntity.ok(updateProduct.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
