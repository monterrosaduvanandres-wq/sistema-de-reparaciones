
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.DetalleReparacion;
import com.reparaciones.sistema.service.DetalleReparacionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/detalles-reparacion")
public class DetalleReparacionController {

    private final DetalleReparacionService service;

    public DetalleReparacionController(
            DetalleReparacionService service) {
        this.service = service;
    }

    @GetMapping
public Page<DetalleReparacion> listar(
        @PageableDefault(
                size = 10,
                sort = "idDetalle",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {
    return service.listar(pageable);
}

    @GetMapping("/{id}")
    public ResponseEntity<DetalleReparacion> buscar(
            @PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleReparacion crear(
            @Valid @RequestBody DetalleReparacion detalle) {

        return service.guardar(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleReparacion> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DetalleReparacion detalle) {

        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        detalle.setIdDetalle(id);

        return ResponseEntity.ok(service.guardar(detalle));
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
