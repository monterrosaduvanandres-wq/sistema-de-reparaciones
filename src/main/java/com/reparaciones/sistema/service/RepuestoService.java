
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Repuesto;
import com.reparaciones.sistema.repository.RepuestoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {
    private final RepuestoRepository repository;

    public RepuestoService(RepuestoRepository repository) {
        this.repository = repository;
    }

    public List<Repuesto> listar() {
        return repository.findAll();
    }

    public Optional<Repuesto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Repuesto guardar(Repuesto repuesto) {
        return repository.save(repuesto);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
