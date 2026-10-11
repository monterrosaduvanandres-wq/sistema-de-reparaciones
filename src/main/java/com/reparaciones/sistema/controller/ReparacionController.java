
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.service.ReparacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reparaciones")
public class ReparacionController {
    private final ReparacionService service;

    public ReparacionController(ReparacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reparacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reparacion> buscar(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reparacion crear(@RequestBody Reparacion reparacion) {
        return service.guardar(reparacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reparacion> actualizar(
            @PathVariable Long id, @RequestBody Reparacion reparacion) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reparacion.setIdReparacion(id);
        return ResponseEntity.ok(service.guardar(reparacion));
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
