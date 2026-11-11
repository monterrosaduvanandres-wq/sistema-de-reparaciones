
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Pago;
import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.repository.PagoRepository;
import com.reparaciones.sistema.repository.ReparacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository repository;
    private final ReparacionRepository reparacionRepository;

    public PagoService(
            PagoRepository repository,
            ReparacionRepository reparacionRepository) {

        this.repository = repository;
        this.reparacionRepository = reparacionRepository;
    }

    public Page<Pago> listar(Pageable pageable) {
    return repository.findAll(pageable);
}

    public Optional<Pago> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Pago guardar(Pago pago) {

        if (pago.getReparacion() == null ||
                pago.getReparacion().getIdReparacion() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar el ID de la reparación"
            );
        }

        Long idReparacion = pago.getReparacion().getIdReparacion();

        Reparacion reparacion = reparacionRepository.findById(idReparacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La reparación indicada no existe"
                ));

        pago.setMetodoPago(pago.getMetodoPago().trim());
        pago.setEstadoPago(pago.getEstadoPago().trim());
        pago.setReparacion(reparacion);

        if (pago.getIdPago() != null) {

            Pago existente = repository.findById(pago.getIdPago())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "El pago no existe"
                    ));

            existente.setFecha(pago.getFecha());
            existente.setMonto(pago.getMonto());
            existente.setMetodoPago(pago.getMetodoPago());
            existente.setEstadoPago(pago.getEstadoPago());
            existente.setReparacion(reparacion);

            return repository.save(existente);
        }

        return repository.save(pago);
    }

    @Transactional
    public void eliminar(Long id) {

        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El pago no existe"
                ));

        repository.delete(pago);
    }
}
