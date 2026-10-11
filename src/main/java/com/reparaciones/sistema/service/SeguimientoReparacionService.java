
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.SeguimientoReparacion;
import com.reparaciones.sistema.repository.SeguimientoReparacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoReparacionService {
    private final SeguimientoReparacionRepository repository;

    public SeguimientoReparacionService(
            SeguimientoReparacionRepository repository) {
        this.repository = repository;
    }

    public List<SeguimientoReparacion> listar() {
        return repository.findAll();
    }

    public Optional<SeguimientoReparacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public SeguimientoReparacion guardar(
            SeguimientoReparacion seguimiento) {
        return repository.save(seguimiento);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
