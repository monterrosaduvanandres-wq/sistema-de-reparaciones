
package com.reparaciones.sistema.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.service.ReparacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/reparaciones")
public class ReparacionController {

    private final ReparacionService service;

    public ReparacionController(ReparacionService service) {
        this.service = service;
    }

    @GetMapping
public Page<Reparacion> listar(
        @PageableDefault(
                size = 5,
                sort = "idReparacion",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {
    return service.listar(pageable);
}

    @GetMapping("/{id}")
    public ResponseEntity<Reparacion> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reparacion crear(@Valid @RequestBody Reparacion reparacion) {
        return service.guardar(reparacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reparacion> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Reparacion reparacion) {

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
