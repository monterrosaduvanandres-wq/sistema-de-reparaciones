
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.Repuesto;
import com.reparaciones.sistema.service.RepuestoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
    private final RepuestoService service;

    public RepuestoController(RepuestoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Repuesto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> buscar(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Repuesto crear(@RequestBody Repuesto repuesto) {
        return service.guardar(repuesto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> actualizar(
            @PathVariable Long id, @RequestBody Repuesto repuesto) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repuesto.setIdRepuesto(id);
        return ResponseEntity.ok(service.guardar(repuesto));
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
