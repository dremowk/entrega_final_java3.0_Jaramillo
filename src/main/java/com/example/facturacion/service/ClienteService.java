package com.example.facturacion.service;

import com.example.facturacion.model.Cliente;
import com.example.facturacion.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ✅ Crear cliente
    public Cliente crear(Cliente cliente) {

        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con ese email");
        }

        return clienteRepository.save(cliente);
    }

    // ✅ NUEVO: listar todos los clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // ✅ NUEVO: buscar cliente por ID
   public Cliente obtenerPorId(Long id) {
    return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
}


    public void eliminar(Long id) {
    if (!clienteRepository.existsById(id)) {
        throw new RuntimeException("Cliente no existe");
    }
    clienteRepository.deleteById(id);
}

}
