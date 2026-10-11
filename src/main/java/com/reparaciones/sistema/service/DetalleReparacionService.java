
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.DetalleReparacion;
import com.reparaciones.sistema.repository.DetalleReparacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleReparacionService {
    private final DetalleReparacionRepository repository;

    public DetalleReparacionService(
            DetalleReparacionRepository repository) {
        this.repository = repository;
    }

    public List<DetalleReparacion> listar() {
        return repository.findAll();
    }

    public Optional<DetalleReparacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public DetalleReparacion guardar(DetalleReparacion detalle) {
        return repository.save(detalle);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
