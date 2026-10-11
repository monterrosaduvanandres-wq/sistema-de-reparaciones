
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparacionRepository
        extends JpaRepository<Reparacion, Long> {

    List<Reparacion> findByEstado(String estado);

    List<Reparacion> findByTecnico(Tecnico tecnico);

    List<Reparacion> findByCelular(Celular celular);
}
