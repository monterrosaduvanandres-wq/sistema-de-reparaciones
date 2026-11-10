
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Repuesto;
import com.reparaciones.sistema.entity.Proveedor;
import com.reparaciones.sistema.repository.RepuestoRepository;
import com.reparaciones.sistema.repository.ProveedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    public List<Repuesto> listar() {
        return repository.findAll();
    }

    public Optional<Repuesto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Repuesto guardar(Repuesto repuesto) {

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

        repuesto.setProveedor(proveedor);

        return repository.save(repuesto);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
