package com.example.facturacion.controller;

import com.example.facturacion.dto.FacturaRequestDTO;
import com.example.facturacion.dto.ItemLineaDTO;
import com.example.facturacion.model.*;
import com.example.facturacion.repository.ClienteRepository;
import com.example.facturacion.repository.FacturaRepository;
import com.example.facturacion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @PostMapping
    public Factura crearFactura(@RequestBody FacturaRequestDTO request) {

        // 1. Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(request.getCliente().getClienteid())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // 2. Crear la factura base
        Factura factura = new Factura();
        factura.setCliente(cliente);

        // 3. Procesar las líneas (items)
        for (ItemLineaDTO linea : request.getLineas()) {

            // Validar producto
            Producto producto = productoRepository.findById(linea.getProducto().getProductoid())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Crear item
            ItemFactura item = new ItemFactura();
            item.setProducto(producto);
            item.setCantidad(linea.getCantidad());

            // Agregar item a la factura
            factura.addItem(item);
        }

        // 4. Calcular total
        factura.calcularTotal();

        // 5. Guardar factura con sus items
        return facturaRepository.save(factura);
    }
}
