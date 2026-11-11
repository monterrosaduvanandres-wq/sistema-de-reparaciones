
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.SeguimientoReparacion;
import com.reparaciones.sistema.service.SeguimientoReparacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;


@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoReparacionController {

    private final SeguimientoReparacionService service;

    public SeguimientoReparacionController(
            SeguimientoReparacionService service) {
        this.service = service;
    }

    @GetMapping
public Page<SeguimientoReparacion> listar(
        @PageableDefault(
                size = 10,
                sort = "idSeguimiento",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {
    return service.listar(pageable);
}

    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoReparacion> buscar(
            @PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SeguimientoReparacion crear(
            @Valid @RequestBody SeguimientoReparacion seguimiento) {
        return service.guardar(seguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoReparacion> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SeguimientoReparacion seguimiento) {

        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        seguimiento.setIdSeguimiento(id);

        return ResponseEntity.ok(service.guardar(seguimiento));
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
