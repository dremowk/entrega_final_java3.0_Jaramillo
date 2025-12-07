package com.example.facturacion.dto;

import lombok.Data;
import java.util.List;

@Data
public class FacturaRequestDTO {

    private ClienteRef cliente;
    private String descripcion;    // <-- Opcional, pero así tu servicio lo puede leer
    private List<ItemLineaDTO> lineas;

    @Data
    public static class ClienteRef {
        private Long clienteid;
    }

    @Data
    public static class ProductoRef {
        private Long productoid;
    }
}
