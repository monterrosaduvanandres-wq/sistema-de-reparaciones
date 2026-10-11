
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.SeguimientoReparacion;
import com.reparaciones.sistema.entity.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguimientoReparacionRepository
        extends JpaRepository<SeguimientoReparacion, Long> {

    List<SeguimientoReparacion> findByEstado(String estado);

    List<SeguimientoReparacion> findByReparacion(Reparacion reparacion);
}
