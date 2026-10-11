
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Pago;
import com.reparaciones.sistema.entity.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByEstadoPago(String estadoPago);

    List<Pago> findByMetodoPago(String metodoPago);

    List<Pago> findByReparacion(Reparacion reparacion);
}
