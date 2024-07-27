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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {
    @Autowired private InvoiceRepository repository;
    @Autowired private InvoiceDetailsRepository invoiceDetailsRepository;
    @Autowired private ClientsRepository clientsRepository;


    public Invoice generateInvoice(Long clientId){
        Optional<Client> clientOpt = clientsRepository.findById(clientId);
        if(clientOpt.isPresent()){
            Client client = clientOpt.get();
            List<Invoice_details> details = invoiceDetailsRepository.findByClientAndDelivered(client, false);
            if(details.isEmpty()){
                throw new RuntimeException("No se encontro productos en el carrito");
            }else{
                Invoice invoice = new Invoice();
                invoice.setClient(client);
                invoice.setCreatedAt(new Date());
                double total = 0.0;
                for (Invoice_details details1 : details){
                    total += details1.getAmount() * details1.getPrice();
                    details1.setDelivered(true);
                }
                invoice.setTotal(total);
                return repository.save(invoice);
            }
        }else{
            throw new RuntimeException("CLiente no encontrado");
        }
    }

    public Invoice getInvoicesByClientId(Long clientId){
        Optional<Client> client = clientsRepository.findById(clientId);
        if(client.isPresent()){
            List<Invoice> invoices = client.get().getInvoice();
            if(invoices.isEmpty()){
                throw new RuntimeException("No se encontraron comprobantes generados por este cliente");
            }
            Invoice lastInvoice = invoices.get(invoices.size() - 1);
            return lastInvoice;
        }else{
            throw new RuntimeException("Cliente no encontrado");
        }

    }


}
