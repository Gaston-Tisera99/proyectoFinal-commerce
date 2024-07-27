    package com.Cliente.ApiRest.controllers;

    import com.Cliente.ApiRest.entities.Client;
    import com.Cliente.ApiRest.entities.Invoice;
    import com.Cliente.ApiRest.entities.Invoice_details;
    import com.Cliente.ApiRest.entities.Product;
    import com.Cliente.ApiRest.services.ClientsService;
    import com.Cliente.ApiRest.services.InvoicesService;
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

    import javax.swing.text.html.Option;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/api/v1/invoices")
    @Tag(name="Rutas de Invoices", description = "CRUD de comprobantes")
    public class InvoicesControllers {
        @Autowired private InvoicesService service;
        @Autowired private ClientsService clienteService;
        @Autowired private ProductsService productsService;

        @Operation(summary = "Generar un comprobante", description = "Generar un comprobante de un cliente con el importe total del pedido")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Comprobante generado exitosamente", content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "404", description = "Cliente o carrito no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error de Servidor")
        })
        @PostMapping
        public ResponseEntity<Invoice> generateInvoice(@RequestParam Long clientId){
            try {
                Invoice invoice = service.generateInvoice(clientId);
                return ResponseEntity.ok(invoice);
            }catch (RuntimeException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @Operation(summary = "Obtener Comprobante por el id de un cliente", description = "Devuelve una lista de comprobantes pertenecientes a un cliente")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Comprobante encontrado exitosamente", content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "404", description = "Cliente no encontrados"),
                @ApiResponse(responseCode = "500", description = "Error de Servidor")
        })
        @GetMapping("/{clientId}")
        public ResponseEntity<Invoice> getInvoicesByClientId(@PathVariable Long clientId){
            try {
                Invoice invoices = service.getInvoicesByClientId(clientId);
                return ResponseEntity.ok(invoices);
            }catch(RuntimeException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
