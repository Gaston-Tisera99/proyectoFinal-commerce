package com.Cliente.ApiRest.services;

import com.Cliente.ApiRest.entities.Client;
import com.Cliente.ApiRest.entities.Product;
import com.Cliente.ApiRest.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository repository;

    public Client saveClient(Client client){
        return repository.save(client);
    }

    public List<Client> readClients(){
        return repository.findAll();
    }

    public Optional<Client> readOneClient(Long id){
        return repository.findById(id);
    }


    public void destroyOneClient(Long id){
        repository.deleteById(id);
    }

    public Optional<Client> updateClient(Long id, Client newData) {
        return repository.findById(id).map(client -> {
            client.setName(newData.getName());
            client.setLastname(newData.getLastname());
            client.setDocnumber(newData.getDocnumber());
            return repository.save(client);
        });
    }

}
