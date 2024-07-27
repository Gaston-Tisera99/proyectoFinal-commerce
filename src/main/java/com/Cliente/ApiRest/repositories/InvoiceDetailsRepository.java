package com.Cliente.ApiRest.repositories;

import java.util.List;

import com.Cliente.ApiRest.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Cliente.ApiRest.entities.Invoice_details;

@Repository
public interface InvoiceDetailsRepository extends JpaRepository<Invoice_details, Long> {
    List<Invoice_details> findByClientAndDelivered(Client client, Boolean delivered);
}
