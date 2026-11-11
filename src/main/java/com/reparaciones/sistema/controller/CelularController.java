package com.reparaciones.sistema.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.service.CelularService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/celulares")
public class CelularController {

    private final CelularService service;

    public CelularController(CelularService service) {
        this.service = service;
    }

    @GetMapping
public Page<Celular> listar(
        @PageableDefault(
                size = 5,
                sort = "idCelular",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {

    return service.listar(pageable);
}

    @GetMapping("/{id}")
    public ResponseEntity<Celular> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Celular crear(@Valid @RequestBody Celular celular) {
        return service.guardar(celular);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Celular> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Celular celular) {

        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        celular.setIdCelular(id);

        return ResponseEntity.ok(service.guardar(celular));
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