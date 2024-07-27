package com.Cliente.ApiRest.controllers;


import com.Cliente.ApiRest.entities.Client;
import com.Cliente.ApiRest.services.ClientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/auth")
@Tag(name="Rutas de clientes", description = "CRUD de clientes")
public class ClientsController {
    @Autowired private ClientsService service;

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo cliente", description = "Registrar un nuevo cliente proporcionando los datos necesarios.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Actualizar los datos del cliente", description = "Actualizar los datos del cliente utilizando los datos proporcionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
