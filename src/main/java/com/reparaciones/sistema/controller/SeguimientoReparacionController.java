
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.SeguimientoReparacion;
import com.reparaciones.sistema.service.SeguimientoReparacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoReparacionController {
    private final SeguimientoReparacionService service;

    public SeguimientoReparacionController(
            SeguimientoReparacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<SeguimientoReparacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoReparacion> buscar(
            @PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SeguimientoReparacion crear(
            @RequestBody SeguimientoReparacion seguimiento) {
        return service.guardar(seguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoReparacion> actualizar(
            @PathVariable Long id,
            @RequestBody SeguimientoReparacion seguimiento) {
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

