
package com.reparaciones.sistema.controller;

import com.reparaciones.sistema.entity.Pago;
import com.reparaciones.sistema.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @GetMapping
public Page<Pago> listar(
        @PageableDefault(
                size = 5,
                sort = "fecha",
                direction = Sort.Direction.DESC
        ) Pageable pageable) {

    return service.listar(pageable);
}

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pago crear(@Valid @RequestBody Pago pago) {
        return service.guardar(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Pago pago) {

        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pago.setIdPago(id);

        return ResponseEntity.ok(service.guardar(pago));
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
