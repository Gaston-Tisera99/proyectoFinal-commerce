package com.Cliente.ApiRest.repositories;

import com.Cliente.ApiRest.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Client, Long> {
}
