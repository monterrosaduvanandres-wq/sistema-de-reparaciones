
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.entity.Tecnico;
import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.repository.ReparacionRepository;
import com.reparaciones.sistema.repository.TecnicoRepository;
import com.reparaciones.sistema.repository.CelularRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import java.util.Optional;

@Service
public class ReparacionService {

    private final ReparacionRepository repository;
    private final TecnicoRepository tecnicoRepository;
    private final CelularRepository celularRepository;

    public ReparacionService(
            ReparacionRepository repository,
            TecnicoRepository tecnicoRepository,
            CelularRepository celularRepository) {

        this.repository = repository;
        this.tecnicoRepository = tecnicoRepository;
        this.celularRepository = celularRepository;
    }

   public Page<Reparacion> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<Reparacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Reparacion guardar(Reparacion reparacion) {

        // Validar fechas
        validarFechas(reparacion);

        // Verificar que el tecnico exista
        if (reparacion.getTecnico() == null ||
                reparacion.getTecnico().getIdTecnico() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar un tecnico valido"
            );
        }

        Tecnico tecnico = tecnicoRepository
                .findById(reparacion.getTecnico().getIdTecnico())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El tecnico seleccionado no existe"
                ));

        // Verificar que el celular exista
        if (reparacion.getCelular() == null ||
                reparacion.getCelular().getIdCelular() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar un celular valido"
            );
        }

        Celular celular = celularRepository
                .findById(reparacion.getCelular().getIdCelular())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El celular seleccionado no existe"
                ));

        // Normalizar textos
        reparacion.setProblemaReportado(
                reparacion.getProblemaReportado().trim()
        );

        reparacion.setEstado(reparacion.getEstado().trim());

        if (reparacion.getDiagnostico() != null) {
            reparacion.setDiagnostico(
                    reparacion.getDiagnostico().trim()
            );
        }

        // Usar las entidades completas
        reparacion.setTecnico(tecnico);
        reparacion.setCelular(celular);

        // Si es una actualización, modificar el registro existente
        if (reparacion.getIdReparacion() != null) {

            Reparacion existente = repository
                    .findById(reparacion.getIdReparacion())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "La reparacion no existe"
                    ));

            existente.setFechaIngreso(reparacion.getFechaIngreso());
            existente.setFechaEstimada(reparacion.getFechaEstimada());
            existente.setFechaEntrega(reparacion.getFechaEntrega());
            existente.setDiagnostico(reparacion.getDiagnostico());
            existente.setProblemaReportado(reparacion.getProblemaReportado());
            existente.setEstado(reparacion.getEstado());
            existente.setTecnico(tecnico);
            existente.setCelular(celular);

            return repository.save(existente);
        }

        return repository.save(reparacion);
    }

    private void validarFechas(Reparacion reparacion) {

        LocalDate ingreso = reparacion.getFechaIngreso();
        LocalDate estimada = reparacion.getFechaEstimada();
        LocalDate entrega = reparacion.getFechaEntrega();

        if (ingreso == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de ingreso es obligatoria"
            );
        }

        if (estimada != null && estimada.isBefore(ingreso)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha estimada no puede ser anterior a la fecha de ingreso"
            );
        }

        if (entrega != null && entrega.isBefore(ingreso)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de entrega no puede ser anterior a la fecha de ingreso"
            );
        }

        if (entrega != null && estimada != null &&
                entrega.isBefore(estimada)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de entrega no puede ser anterior a la fecha estimada"
            );
        }
    }

    @Transactional
    public void eliminar(Long id) {

        Reparacion reparacion = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La reparacion no existe"
                ));

        boolean tieneSeguimientos = reparacion.getSeguimientos() != null
                && !reparacion.getSeguimientos().isEmpty();

        boolean tieneDetalles = reparacion.getDetalles() != null
                && !reparacion.getDetalles().isEmpty();

        boolean tienePagos = reparacion.getPagos() != null
                && !reparacion.getPagos().isEmpty();

        if (tieneSeguimientos || tieneDetalles || tienePagos) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la reparacion porque tiene seguimientos, detalles o pagos asociados"
            );
        }

        repository.delete(reparacion);
    }
}
