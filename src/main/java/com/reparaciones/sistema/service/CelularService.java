
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.repository.CelularRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CelularService {
    private final CelularRepository repository;

    public CelularService(CelularRepository repository) {
        this.repository = repository;
    }

    public List<Celular> listar() {
        return repository.findAll();
    }

    public Optional<Celular> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Celular guardar(Celular celular) {
        return repository.save(celular);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
