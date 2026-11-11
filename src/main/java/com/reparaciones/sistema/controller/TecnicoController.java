
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.Tecnico;
import com.reparaciones.sistema.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;


@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
public Page<Tecnico> listar(
        @PageableDefault(
                size = 5,
                sort = "idTecnico",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {

    return service.listar(pageable);
}

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tecnico crear(@Valid @RequestBody Tecnico tecnico) {
        return service.guardar(tecnico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Tecnico tecnico) {

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
