
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.Tecnico;
import com.reparaciones.sistema.service.TecnicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {
    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tecnico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscar(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tecnico crear(@RequestBody Tecnico tecnico) {
        return service.guardar(tecnico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizar(
            @PathVariable Long id, @RequestBody Tecnico tecnico) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tecnico.setIdTecnico(id);
        return ResponseEntity.ok(service.guardar(tecnico));
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
