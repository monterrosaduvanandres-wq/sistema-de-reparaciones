
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.DetalleReparacion;
import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.entity.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleReparacionRepository
        extends JpaRepository<DetalleReparacion, Long> {

    List<DetalleReparacion> findByReparacion(Reparacion reparacion);

    List<DetalleReparacion> findByRepuesto(Repuesto repuesto);
}
