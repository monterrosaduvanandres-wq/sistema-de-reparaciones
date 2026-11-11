
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Tecnico;
import com.reparaciones.sistema.repository.TecnicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Service
public class TecnicoService {

    private final TecnicoRepository repository;

    public TecnicoService(TecnicoRepository repository) {
        this.repository = repository;
    }

    public Page<Tecnico> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<Tecnico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Tecnico guardar(Tecnico tecnico) {

        // Limpiar espacios innecesarios
        if (tecnico.getNombre() != null) {
            tecnico.setNombre(tecnico.getNombre().trim());
        }

        if (tecnico.getApellido() != null) {
            tecnico.setApellido(tecnico.getApellido().trim());
        }

        if (tecnico.getTelefono() != null) {
            tecnico.setTelefono(tecnico.getTelefono().trim());
        }

        if (tecnico.getEspecialidad() != null) {
            tecnico.setEspecialidad(tecnico.getEspecialidad().trim());
        }

        return repository.save(tecnico);
    }

    @Transactional
    public void eliminar(Long id) {

        Tecnico tecnico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El técnico con ID " + id + " no existe"
                ));

        // No permitir eliminar técnicos con reparaciones asignadas
        if (tecnico.getReparaciones() != null &&
                !tecnico.getReparaciones().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el técnico porque tiene reparaciones asignadas"
            );
        }

        repository.delete(tecnico);
    }
}
