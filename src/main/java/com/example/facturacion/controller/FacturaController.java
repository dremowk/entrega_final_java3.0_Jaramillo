package com.example.facturacion.controller;

import com.example.facturacion.dto.FacturaRequestDTO;
import com.example.facturacion.model.Factura;
import com.example.facturacion.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    // A) CREAR FACTURA
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaRequestDTO dto) {
        Factura factura = facturaService.crearFactura(dto);
        return ResponseEntity.ok(factura);
    }

    // B) LISTAR TODAS LAS FACTURAS
    @GetMapping
    public ResponseEntity<List<Factura>> listarFacturas() {
        return ResponseEntity.ok(facturaService.listarFacturas());
    }

    // C) CONSULTAR FACTURA POR ID (detalle completo)
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerFactura(id));
    }

    // D) ELIMINAR FACTURA
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.ok("Factura eliminada correctamente");
    }
}
