
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Repuesto;
import com.reparaciones.sistema.entity.Proveedor;

import com.reparaciones.sistema.repository.RepuestoRepository;
import com.reparaciones.sistema.repository.ProveedorRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

import java.util.Optional;

@Service
public class RepuestoService {

    private final RepuestoRepository repository;
    private final ProveedorRepository proveedorRepository;

    public RepuestoService(
            RepuestoRepository repository,
            ProveedorRepository proveedorRepository) {

        this.repository = repository;
        this.proveedorRepository = proveedorRepository;
    }

    public Page<Repuesto> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<Repuesto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Repuesto guardar(Repuesto repuesto) {

        // Validar proveedor
        if (repuesto.getProveedor() == null ||
                repuesto.getProveedor().getIdProveedor() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar un proveedor"
            );
        }

        Long idProveedor = repuesto.getProveedor().getIdProveedor();

        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El proveedor con ID " + idProveedor + " no existe"
                ));

        // Validar precio
        if (repuesto.getPrecio() == null ||
                repuesto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio debe ser mayor que cero"
            );
        }

        // Validar stock
        if (repuesto.getStock() == null || repuesto.getStock() < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El stock no puede ser nulo ni negativo"
            );
        }

        // Limpiar textos
        repuesto.setNombre(repuesto.getNombre().trim());

        if (repuesto.getDescripcion() != null) {
            repuesto.setDescripcion(repuesto.getDescripcion().trim());
        }

        repuesto.setProveedor(proveedor);

        // Si es una actualización, conservar los detalles asociados.
        if (repuesto.getIdRepuesto() != null) {

            Repuesto existente = repository.findById(
                    repuesto.getIdRepuesto()
            ).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El repuesto no existe"
            ));

            existente.setNombre(repuesto.getNombre());
            existente.setDescripcion(repuesto.getDescripcion());
            existente.setPrecio(repuesto.getPrecio());
            existente.setStock(repuesto.getStock());
            existente.setProveedor(proveedor);

            return repository.save(existente);
        }

        return repository.save(repuesto);
    }

    @Transactional
    public void eliminar(Long id) {

        Repuesto repuesto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El repuesto no existe"
                ));

        // Comprobar si el repuesto está siendo utilizado.
        boolean tieneDetalles = repuesto.getDetalles() != null
                && !repuesto.getDetalles().isEmpty();

        if (tieneDetalles) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el repuesto porque está asociado a detalles de reparación"
            );
        }

        repository.delete(repuesto);
    }
}
