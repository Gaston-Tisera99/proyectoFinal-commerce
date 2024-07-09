package com.Cliente.ApiRest.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="invoice_details")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice_details {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @Getter @Setter private Invoice invoice;

    @Getter @Setter private int amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Getter @Setter private Product product;

    @Getter @Setter private double price;

    public void calculatePrice() {
        if (product != null) {
            this.price = product.getPrice() * this.amount;
        }
    }
}
