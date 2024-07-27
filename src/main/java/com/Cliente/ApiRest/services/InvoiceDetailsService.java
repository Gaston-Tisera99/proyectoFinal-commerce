package com.Cliente.ApiRest.services;

import com.Cliente.ApiRest.entities.Client;
import com.Cliente.ApiRest.entities.Invoice_details;
import com.Cliente.ApiRest.entities.Product;
import com.Cliente.ApiRest.repositories.ClientsRepository;
import com.Cliente.ApiRest.repositories.InvoiceDetailsRepository;
import com.Cliente.ApiRest.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsService {
    @Autowired private InvoiceDetailsRepository repository;
    @Autowired private ProductsRepository productsRepository;
    @Autowired private ClientsRepository clientsRepository;

    public Invoice_details addProductToDetail(Long clientId, Long productId, Integer amount){
        Optional<Client> client = clientsRepository.findById(clientId);
        Optional<Product> product = productsRepository.findById(productId);
        if(client.isPresent() & product.isPresent()){
            Invoice_details details = new Invoice_details();
            details.setClient(client.get());
            details.setProduct(product.get());
            details.setPrice(product.get().getPrice());
            details.setAmount(amount);
            details.setDelivered(false);
            return repository.save(details);
        }else{
            throw new RuntimeException("Cliente o Producto no encontrado");
        }
    }

    public Invoice_details removeDetail(Long InvoiceId){
        Optional<Invoice_details> details = repository.findById(InvoiceId);
        if (details.isPresent()){
            repository.deleteById(InvoiceId);
            return details.get();
        }else{
            throw new RuntimeException("Detalle no encontrado");
        }
    }

    public List<Invoice_details> findByClientAndDelivered(Long clientId){
        Client client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        List<Invoice_details> details = repository.findByClientAndDelivered(client, false);
        if (details.isEmpty()) {
            throw new RuntimeException("Detalle de comprobante no encontrado");
        } else {
            return details;
        }
    }
}
