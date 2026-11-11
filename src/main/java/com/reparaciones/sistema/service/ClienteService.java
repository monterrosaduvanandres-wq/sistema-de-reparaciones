
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Cliente;
import com.reparaciones.sistema.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Page<Cliente> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
