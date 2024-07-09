package com.Cliente.ApiRest.repositories;

import com.Cliente.ApiRest.entities.Invoice;
import com.Cliente.ApiRest.entities.Invoice_details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceDetailsRepository extends JpaRepository<Invoice_details, Long> {
    //Optional<Invoice> findByIdWithInvoice(Long id);
}
