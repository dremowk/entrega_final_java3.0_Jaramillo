package com.example.facturacion.service;

import com.example.facturacion.dto.FacturaRequestDTO;
import com.example.facturacion.dto.ItemLineaDTO;
import com.example.facturacion.model.Cliente;
import com.example.facturacion.model.Factura;
import com.example.facturacion.model.ItemFactura;
import com.example.facturacion.model.Producto;
import com.example.facturacion.repository.ClienteRepository;
import com.example.facturacion.repository.FacturaRepository;
import com.example.facturacion.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final FacturaRepository facturaRepository;

    public FacturaService(ClienteRepository clienteRepository,
                          ProductoRepository productoRepository,
                          FacturaRepository facturaRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.facturaRepository = facturaRepository;
    }

    @Transactional
    public Factura crearFactura(FacturaRequestDTO dto) {

        Cliente cliente = clienteRepository
                .findById(dto.getCliente().getClienteid())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Factura factura = new Factura();
        factura.setCliente(cliente);

        for (ItemLineaDTO lineaDTO : dto.getLineas()) {

            Producto producto = productoRepository.findById(lineaDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ItemFactura item = new ItemFactura();
            item.setProducto(producto);
            item.setCantidad(lineaDTO.getCantidad());

            factura.addItem(item);
        }

        factura.calcularTotal();
        return facturaRepository.save(factura);
    }
}
