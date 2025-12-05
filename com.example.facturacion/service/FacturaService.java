package com.example.facturacion.service;

import com.example.facturacion.model.Factura;
import com.example.facturacion.model.ItemFactura;
import com.example.facturacion.model.Producto;
import com.example.facturacion.repository.FacturaRepository;
import com.example.facturacion.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Factura crearFactura(Factura factura) {

        // Asociar los items con la factura y validar cada producto
        factura.getItems().forEach(item -> {
            Long productoId = item.getProducto().getId();

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoId));

            item.setProducto(producto);
            item.setFactura(factura);
        });

        // Calcular el total de la factura
        double total = factura.getItems()
                .stream()
                .mapToDouble(ItemFactura::getSubtotal)
                .sum();

        factura.setTotal(total);

        return facturaRepository.save(factura);
    }
}
