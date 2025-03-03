package com.Cliente.ApiRest.repositories;

import com.Cliente.ApiRest.entities.Client;
import com.Cliente.ApiRest.entities.Invoice;
import com.Cliente.ApiRest.entities.Invoice_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice_details> findInvoiceDetailById(@Param("id") Long id);
}
