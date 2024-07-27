package com.Cliente.ApiRest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="invoice_details")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice_details {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Getter @Setter
    private Client client;

    @Getter @Setter private double price;

    @Getter @Setter private int amount;

    @Getter @Setter private boolean delivered;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    @Getter @Setter
    private Product product;
}
