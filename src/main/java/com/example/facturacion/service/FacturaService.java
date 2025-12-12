package com.example.facturacion.service;

import com.example.facturacion.dto.FacturaRequestDTO;
import com.example.facturacion.model.Cliente;
import com.example.facturacion.model.Factura;
import com.example.facturacion.model.ItemFactura;
import com.example.facturacion.model.Producto;
import com.example.facturacion.repository.ClienteRepository;
import com.example.facturacion.repository.FacturaRepository;
import com.example.facturacion.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public FacturaService(FacturaRepository facturaRepository,
                          ClienteRepository clienteRepository,
                          ProductoRepository productoRepository) {

        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    public Factura crearFactura(FacturaRequestDTO dto) {

        // CLIENTE
        Cliente cliente = clienteRepository.findById(dto.getCliente().getClienteid())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Factura factura = new Factura();
        factura.setCliente(cliente);

        // ITEMS
        dto.getLineas().forEach(itemDTO -> {
            Producto producto = productoRepository.findById(itemDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < itemDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // descontar stock
            producto.setStock(producto.getStock() - itemDTO.getCantidad());
            productoRepository.save(producto);

            ItemFactura item = new ItemFactura();
            item.setProducto(producto);
            item.setCantidad(itemDTO.getCantidad());
            item.setFactura(factura);

            factura.getItems().add(item);
        });

        factura.calcularTotal();

        return facturaRepository.save(factura);
    }

    // Listar todas las facturas
    public java.util.List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    // Consultar factura por ID
    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Eliminar factura
    public void eliminar(Long id) {
        if (!facturaRepository.existsById(id)) {
            throw new RuntimeException("La factura no existe");
        }
        facturaRepository.deleteById(id);
    }
}
