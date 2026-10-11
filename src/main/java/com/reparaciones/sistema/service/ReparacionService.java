
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.repository.ReparacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReparacionService {
    private final ReparacionRepository repository;

    public ReparacionService(ReparacionRepository repository) {
        this.repository = repository;
    }

    public List<Reparacion> listar() {
        return repository.findAll();
    }

    public Optional<Reparacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Reparacion guardar(Reparacion reparacion) {
        return repository.save(reparacion);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
