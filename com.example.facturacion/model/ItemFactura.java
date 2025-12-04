package com.example.facturacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "items_factura")
@Data
public class ItemFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    // Relación con Producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Relación con Factura (Dueño de la relación)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    // Helper para calcular el subtotal
    public Double getSubtotal() {
        if (producto != null && producto.getPrecio() != null) {
            return cantidad * producto.getPrecio();
        }
        return 0.0;
    }
}