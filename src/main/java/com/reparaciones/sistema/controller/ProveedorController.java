
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.Proveedor;
import com.reparaciones.sistema.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;


@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
public Page<Proveedor> listar(
        @PageableDefault(
                size = 5,
                sort = "idProveedor",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {
    return service.listar(pageable);
}
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Proveedor crear(@Valid @RequestBody Proveedor proveedor) {
        return service.guardar(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Proveedor proveedor) {

        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        proveedor.setIdProveedor(id);

        return ResponseEntity.ok(service.guardar(proveedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
