package com.Cliente.ApiRest.controllers;

import com.Cliente.ApiRest.entities.Invoice_details;
import com.Cliente.ApiRest.services.ClientsService;
import com.Cliente.ApiRest.services.InvoiceDetailsService;
import com.Cliente.ApiRest.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/carts")
@Tag(name="Rutas de Details Invoices", description = "CRUD de detalles de comprobante")
public class InvoiceDetailsController {
    @Autowired
    private InvoiceDetailsService detailsService;

    @Operation(summary = "Agregar Producto al carrito", description = "Agregar productos de un cliente en especifico al carrito con una cantidad especifica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto agregado exitosamente al carrito", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Cliente o Producto no encontrados"),
            @ApiResponse(responseCode = "500", description = "Error de Servidor")
    })
    @PostMapping("/{clientId}/{productId}/{quantity}")
    public ResponseEntity<Invoice_details> addProductToDetail(
            @PathVariable("clientId") Long clientId,
            @PathVariable("productId") Long productId,
            @PathVariable("quantity") Integer quantity) {
        try {
            Invoice_details details = detailsService.addProductToDetail(clientId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(details);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Sacar un Producto del carrito", description = "Sacar un producto de un cliente del carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto retirado exitosamente del carrito", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error de Servidor")
    })
    @DeleteMapping("/{detailId}")
    public ResponseEntity<Void> removeProductFromDetail(@PathVariable Long detailId){
        try {
            detailsService.removeDetail(detailId);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Encontrar Detalle de comprobante por cliente y estado de entrega", description = "Retorna una lista de detalles de comprobante pertenecientes a un cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalles de comprobante encontrados", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error de Servidor")
    })
    @GetMapping("/{clid}")
    public ResponseEntity<List<Invoice_details>> findByClientIdAndDelivered(@PathVariable Long clid) {
        try {
            List<Invoice_details> details = detailsService.findByClientAndDelivered(clid);
            return ResponseEntity.ok(details);
        } catch (RuntimeException e) {
            // Verificar el mensaje de la excepción para decidir la respuesta
            if ("Cliente no encontrado".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else if ("Detalle de comprobante no encontrado".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
