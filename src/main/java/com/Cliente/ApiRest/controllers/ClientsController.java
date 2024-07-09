package com.Cliente.ApiRest.controllers;


import com.Cliente.ApiRest.entities.Client;
import com.Cliente.ApiRest.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/auth")
public class ClientsController {
    @Autowired private ClientsService service;

    @PostMapping("/register")
    public ResponseEntity<Client> createClient(@RequestBody Client data){
        try{
            Client client = service.saveClient(data);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/me")
    public ResponseEntity<Client> updateClient(@RequestBody Client newData) {
        try {

            Long id = newData.getId();
            // Llama al método del servicio para actualizar el cliente con el ID proporcionado
            Optional<Client> updatedClient = service.updateClient(id, newData);

            // Verifica si se encontró y actualizó correctamente el cliente
            if (updatedClient.isPresent()) {
                // Si se encontró y actualizó correctamente, devuelve el cliente actualizado con estado HTTP 200 OK
                return ResponseEntity.ok(updatedClient.get());
            } else {
                // Si no se encontró el cliente con el ID dado, devuelve un error 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            // Si ocurre alguna excepción durante la actualización del cliente, captúrala y maneja el error
            System.out.println(e.getMessage());  // Imprime el mensaje de la excepción en la consola (esto podría ser mejorado)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Devuelve un error 500 Internal Server Error
        }
    }


}
