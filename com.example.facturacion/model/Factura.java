package com.example.facturacion.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // ESTO ES CLAVE: Cumple el requisito de "modificaciones en cascada"
    // Si guardas una factura, se guardan sus items.
    // Si borras una factura, se borran sus items.
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemFactura> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = new Date();
    }

    // Método helper para añadir items
    public void addItem(ItemFactura item) {
        this.items.add(item);
        item.setFactura(this);
    }
}