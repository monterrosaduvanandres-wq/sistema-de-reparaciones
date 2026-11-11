
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.SeguimientoReparacion;
import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.repository.SeguimientoReparacionRepository;
import com.reparaciones.sistema.repository.ReparacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class SeguimientoReparacionService {

    private final SeguimientoReparacionRepository repository;
    private final ReparacionRepository reparacionRepository;

    public SeguimientoReparacionService(
            SeguimientoReparacionRepository repository,
            ReparacionRepository reparacionRepository) {

        this.repository = repository;
        this.reparacionRepository = reparacionRepository;
    }

   public Page<SeguimientoReparacion> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<SeguimientoReparacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public SeguimientoReparacion guardar(
            SeguimientoReparacion seguimiento) {

        if (seguimiento.getReparacion() == null ||
                seguimiento.getReparacion().getIdReparacion() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar una reparacion valida"
            );
        }

        Long idReparacion =
                seguimiento.getReparacion().getIdReparacion();

        Reparacion reparacion = reparacionRepository.findById(idReparacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La reparacion seleccionada no existe"
                ));

        seguimiento.setEstado(seguimiento.getEstado().trim());

        if (seguimiento.getObservacion() != null) {
            seguimiento.setObservacion(
                    seguimiento.getObservacion().trim()
            );
        }

        seguimiento.setReparacion(reparacion);

        return repository.save(seguimiento);
    }

    @Transactional
    public void eliminar(Long id) {

        SeguimientoReparacion seguimiento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El seguimiento no existe"
                ));

        repository.delete(seguimiento);
    }
}
