package com.Cliente.ApiRest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@ToString @EqualsAndHashCode
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Getter @Setter private Client client;

    // Ajusta según tu base de datos
    @Getter @Setter
    private LocalDateTime createdAt;

    @Getter @Setter
    private double total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<Invoice_details> invoiceDetails;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}