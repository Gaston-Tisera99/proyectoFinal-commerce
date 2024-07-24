package com.Cliente.ApiRest.services;

import com.Cliente.ApiRest.entities.Client;
import com.Cliente.ApiRest.entities.Invoice;
import com.Cliente.ApiRest.entities.Invoice_details;
import com.Cliente.ApiRest.repositories.ClientsRepository;
import com.Cliente.ApiRest.repositories.InvoiceDetailsRepository;
import com.Cliente.ApiRest.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {
    @Autowired private InvoiceRepository repository;
    @Autowired private InvoiceDetailsRepository invoiceDetailsRepository;
    @Autowired private ClientsRepository clientsRepository;

    private List<Invoice_details> detalle;

    public List<Invoice_details> todos(){
        return detalle;
    }

    public Invoice saveInvoice(Invoice invoice){
        return repository.save(invoice);
    }

    public Optional<Invoice> readOneInvoice(Long id){
        return repository.findById(id);
    }

    public Invoice_details saveInvoiceDetail(Invoice_details details){
        return invoiceDetailsRepository.save(details);
    }

    public Invoice getOrCreateCartInvoice(Client client) {
        // Buscar la factura del carrito del cliente
        Optional<Invoice> cartInvoiceOpt = repository.findById(client.getId());

        // Si la factura del carrito existe, devolverla
        if (cartInvoiceOpt.isPresent()) {
            return cartInvoiceOpt.get();
        }

        // Si la factura del carrito no existe, crear una nueva
        Invoice cartInvoice = new Invoice();
        cartInvoice.setClient(client);
        cartInvoice.setCreatedAt(LocalDateTime.now());
        cartInvoice.setTotal(0.0);
        return repository.save(cartInvoice);
    }

    public Optional<Invoice_details> findInvoiceDetailById(Long id) {
        return repository.findInvoiceDetailById(id);
    }

    public void eliminarUnCliente(Long id) {
        try {
            if (invoiceDetailsRepository.existsById(id)) {
                invoiceDetailsRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("El ID proporcionado no existe: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el detalle con ID: " + id, e);
        }
    }

}
