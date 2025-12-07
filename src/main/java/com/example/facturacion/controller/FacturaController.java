package com.example.facturacion.controller;

import com.example.facturacion.dto.FacturaRequestDTO;
import com.example.facturacion.model.Factura;
import com.example.facturacion.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaRequestDTO dto) {
        Factura factura = facturaService.crearFactura(dto);
        return ResponseEntity.ok(factura);
    }
}
