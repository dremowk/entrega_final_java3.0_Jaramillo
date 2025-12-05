package com.example.facturacion.dto;

import lombok.Data;
import java.util.List;

@Data
public class FacturaRequestDTO {

    private ClienteRef cliente;
    private List<ItemLineaDTO> lineas;

    @Data
    public static class ClienteRef {
        private Long clienteid;
    }
}
