package com.Cliente.ApiRest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="products")
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Representa un producto")
public class Product {

    @Schema(description = "Identificador unico del producto", example = "1")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

    @Schema(description = "Descripcion del producto", example = "producto 1")
    @Getter @Setter private String Description;

    @Schema(description = "Codigo del producto", example = "addf")
    @Getter @Setter private String Code;

    @Schema(description = "Stock del producto", example = "50")
    @Getter @Setter private int Stock;

    @Schema(description = "Precio del producto", example = "3200")
    @Getter @Setter private double price;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter private List<Invoice_details> invoiceDetails;


}
