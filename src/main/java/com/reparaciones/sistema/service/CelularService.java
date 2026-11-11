
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.entity.Cliente;
import com.reparaciones.sistema.repository.CelularRepository;
import com.reparaciones.sistema.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CelularService {

    private final CelularRepository repository;
    private final ClienteRepository clienteRepository;

    public CelularService(
            CelularRepository repository,
            ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

   public Page<Celular> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<Celular> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Celular guardar(Celular celular) {

        if (celular.getCliente() == null ||
                celular.getCliente().getIdCliente() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar un cliente"
            );
        }

        Long idCliente = celular.getCliente().getIdCliente();

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El cliente con ID " + idCliente + " no existe"
                ));

        celular.setCliente(cliente);

        return repository.save(celular);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
