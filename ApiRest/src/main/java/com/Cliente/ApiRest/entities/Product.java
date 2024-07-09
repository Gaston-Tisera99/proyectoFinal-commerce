package com.Cliente.ApiRest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="products")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter private Long id;

    @Getter @Setter private String Description;

    @Getter @Setter private String Code;

    @Getter @Setter private int Stock;

    @Getter @Setter private double price;

    /*
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<Invoice_details> invoiceDetails;

     */
}
