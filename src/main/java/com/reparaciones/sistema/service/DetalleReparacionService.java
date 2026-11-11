
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.DetalleReparacion;
import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.entity.Repuesto;

import com.reparaciones.sistema.repository.DetalleReparacionRepository;
import com.reparaciones.sistema.repository.ReparacionRepository;
import com.reparaciones.sistema.repository.RepuestoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Optional;

@Service
public class DetalleReparacionService {

    private final DetalleReparacionRepository repository;
    private final ReparacionRepository reparacionRepository;
    private final RepuestoRepository repuestoRepository;

    public DetalleReparacionService(
            DetalleReparacionRepository repository,
            ReparacionRepository reparacionRepository,
            RepuestoRepository repuestoRepository) {

        this.repository = repository;
        this.reparacionRepository = reparacionRepository;
        this.repuestoRepository = repuestoRepository;
    }

   public Page<DetalleReparacion> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<DetalleReparacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public DetalleReparacion guardar(DetalleReparacion detalle) {

        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor que cero"
            );
        }

        if (detalle.getReparacion() == null ||
                detalle.getReparacion().getIdReparacion() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar una reparacion"
            );
        }

        if (detalle.getRepuesto() == null ||
                detalle.getRepuesto().getIdRepuesto() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar un repuesto"
            );
        }

        Long idReparacion =
                detalle.getReparacion().getIdReparacion();

        Long idRepuesto =
                detalle.getRepuesto().getIdRepuesto();

        Reparacion reparacion = reparacionRepository
                .findById(idReparacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La reparacion seleccionada no existe"
                ));

        // Recuperar el detalle anterior si estamos actualizando.
        if (detalle.getIdDetalle() != null) {

            DetalleReparacion anterior = repository
                    .findById(detalle.getIdDetalle())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "El detalle de reparacion no existe"
                    ));

            Long idRepuestoAnterior =
                    anterior.getRepuesto().getIdRepuesto();

            Repuesto repuestoAnterior = repuestoRepository
                    .findById(idRepuestoAnterior)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "El repuesto anterior no existe"
                    ));

            int stockAnterior = repuestoAnterior.getStock() == null
                    ? 0 : repuestoAnterior.getStock();

            // Devolver las unidades del detalle anterior.
            repuestoAnterior.setStock(
                    stockAnterior + anterior.getCantidad()
            );

            repuestoRepository.save(repuestoAnterior);
        }

        // Buscar el repuesto nuevo.
        Repuesto repuesto = repuestoRepository
                .findById(idRepuesto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El repuesto seleccionado no existe"
                ));

        int stockDisponible = repuesto.getStock() == null
                ? 0 : repuesto.getStock();

        // Comprobar el inventario disponible.
        if (stockDisponible < detalle.getCantidad()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Stock insuficiente. Disponible: " + stockDisponible
            );
        }

        // Comprobar el precio del repuesto.
        if (repuesto.getPrecio() == null ||
                repuesto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El repuesto debe tener un precio mayor que cero"
            );
        }

        // Descontar unidades del inventario.
        repuesto.setStock(
                stockDisponible - detalle.getCantidad()
        );

        repuestoRepository.save(repuesto);

        // Asociar las entidades completas.
        detalle.setReparacion(reparacion);
        detalle.setRepuesto(repuesto);

        // Calcular automáticamente el precio y subtotal.
        BigDecimal precio = repuesto.getPrecio()
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal subtotal = precio
                .multiply(BigDecimal.valueOf(detalle.getCantidad()))
                .setScale(2, RoundingMode.HALF_UP);

        detalle.setPrecioUnitario(precio);
        detalle.setSubTotal(subtotal);

        return repository.save(detalle);
    }

    @Transactional
    public void eliminar(Long id) {

        DetalleReparacion detalle = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El detalle de reparacion no existe"
                ));

        Long idRepuesto = detalle.getRepuesto().getIdRepuesto();

        Repuesto repuesto = repuestoRepository
                .findById(idRepuesto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El repuesto asociado no existe"
                ));

        int stockActual = repuesto.getStock() == null
                ? 0 : repuesto.getStock();

        // Devolver las unidades al inventario.
        repuesto.setStock(stockActual + detalle.getCantidad());

        repuestoRepository.save(repuesto);

        repository.delete(detalle);
    }
}
