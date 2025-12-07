package com.example.facturacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "La factura debe tener un cliente asociado")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ItemFactura> items = new ArrayList<>();

    
    @Column(nullable = false)
    private Double total = 0.0;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = new Date();
    }

    // Añadir items con relación bidireccional
    public void addItem(ItemFactura item) {
        item.setFactura(this);
        this.items.add(item);
    }

    // Calcular total de la factura
    public void calcularTotal() {
        this.total = this.items.stream()
                .mapToDouble(ItemFactura::getSubtotal)  // ← corregido
                .sum();
    }
}
