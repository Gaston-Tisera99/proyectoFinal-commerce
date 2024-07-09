    package com.Cliente.ApiRest.controllers;

    import com.Cliente.ApiRest.entities.Client;
    import com.Cliente.ApiRest.entities.Invoice;
    import com.Cliente.ApiRest.entities.Invoice_details;
    import com.Cliente.ApiRest.entities.Product;
    import com.Cliente.ApiRest.services.ClientsService;
    import com.Cliente.ApiRest.services.InvoicesService;
    import com.Cliente.ApiRest.services.ProductsService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import javax.swing.text.html.Option;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/api/v1")
    public class InvoicesControllers {
        @Autowired private InvoicesService service;
        @Autowired private ClientsService clienteService;
        @Autowired private ProductsService productsService;


        @PostMapping("/carts")
        public ResponseEntity<Invoice_details> addToCart(@RequestBody Invoice_details detailData) {
            try {
                Client client;

                // Obtener el cliente del detalle de la factura
                if (detailData.getInvoice() != null && detailData.getInvoice().getClient() != null) {
                    client = detailData.getInvoice().getClient();
                } else {
                    // Si el cliente no está presente en el detalle de la factura, devolver una respuesta de error
                    return ResponseEntity.badRequest().body(null);
                }



                // Validar la información del producto y la cantidad
                if (detailData.getProduct() == null || detailData.getProduct().getId() <= 0 || detailData.getAmount() <= 0) {
                    return ResponseEntity.badRequest().build();
                }

                // Buscar el producto en la base de datos
                Optional<Product> productOpt = productsService.readOneProduct(detailData.getProduct().getId());
                if (!productOpt.isPresent()) {
                    return ResponseEntity.notFound().build();
                }
                // Obtener el producto desde la base de datos
                Product product = productOpt.get();

                // Obtener el carrito del cliente (si existe) o crear uno nuevo
                Invoice invoice = service.getOrCreateCartInvoice(client);

                // Manejar la lista de detalles de la factura potencialmente null
                List<Invoice_details> invoiceDetails = invoice.getInvoiceDetails();
                if (invoiceDetails == null) {
                    invoiceDetails = new ArrayList<>(); // Inicializar lista vacía
                    invoice.setInvoiceDetails(invoiceDetails);
                }

                // Validar la existencia del producto en el carrito
                boolean productExists = invoiceDetails.stream()
                        .anyMatch(item -> item.getProduct().getId().equals(detailData.getProduct().getId()));

                // Si el producto existe, actualizar la cantidad
                if (productExists) {
                    Invoice_details existingItem = invoiceDetails.stream()
                            .filter(item -> item.getProduct().getId().equals(detailData.getProduct().getId()))
                            .findFirst().get();
                    existingItem.setAmount(existingItem.getAmount() + detailData.getAmount());
                    existingItem.calculatePrice();
                } else {
                    // Si el producto no existe, agregarlo al carrito
                    detailData.setProduct(productOpt.get());
                    detailData.setInvoice(invoice);
                    invoiceDetails.add(detailData);
                }

                // Recalcular el total de la factura
                double total = invoiceDetails.stream()
                        .mapToDouble(item -> item.getProduct().getPrice() * item.getAmount())
                        .sum();
                invoice.setTotal(total);

                // Guardar el carrito actualizado (factura con detalles)
                service.saveInvoice(invoice);

                // Devolver el detalle de la factura agregado
                return new ResponseEntity<>(detailData, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @DeleteMapping("/carts/{detailId}")
        public ResponseEntity<?> deleteFromCart(@PathVariable Long detailId) {
            try {
                // Get the Optional<Invoice_details>
                Optional<Invoice_details> detailOpt = service.findInvoiceDetailById(detailId);
                if (!detailOpt.isPresent()) {
                    return ResponseEntity.notFound().build();
                }

                // Extract the Invoice from the Optional (assuming it's nested)
                Invoice invoice = detailOpt.get().getInvoice(); // Assuming Invoice_details has a getInvoice() method

                // ... rest of the code remains the same (find detail, remove, recalculate, save)

                // Add the missing return statement
                return ResponseEntity.ok().build(); // Assuming successful deletion
            } catch (Exception e) {
                System.out.println("Error al eliminar el producto del carrito: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }
