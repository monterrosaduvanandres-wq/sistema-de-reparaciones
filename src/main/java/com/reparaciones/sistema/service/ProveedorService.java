
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Proveedor;
import com.reparaciones.sistema.repository.ProveedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public Page<Proveedor> listar(Pageable pageable) {
    return repository.findAll(pageable);
}
    public Optional<Proveedor> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Proveedor guardar(Proveedor proveedor) {

        if (proveedor.getNombre() == null ||
                proveedor.getTelefono() == null ||
                proveedor.getDireccion() == null ||
                proveedor.getCorreo() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Todos los campos del proveedor son obligatorios"
            );
        }

        proveedor.setNombre(proveedor.getNombre().trim());
        proveedor.setTelefono(proveedor.getTelefono().trim());
        proveedor.setDireccion(proveedor.getDireccion().trim());
        proveedor.setCorreo(proveedor.getCorreo().trim());

        if (proveedor.getIdProveedor() != null) {

            Proveedor existente = repository.findById(proveedor.getIdProveedor())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Proveedor no encontrado"
                    ));

            existente.setNombre(proveedor.getNombre());
            existente.setTelefono(proveedor.getTelefono());
            existente.setDireccion(proveedor.getDireccion());
            existente.setCorreo(proveedor.getCorreo());

            return repository.save(existente);
        }

        return repository.save(proveedor);
    }

    @Transactional
    public void eliminar(Long id) {

        Proveedor proveedor = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Proveedor no encontrado"
                ));

        if (proveedor.getRepuestos() != null &&
                !proveedor.getRepuestos().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el proveedor porque tiene repuestos asociados"
            );
        }

        repository.delete(proveedor);
    }
}
