
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    List<Tecnico> findByNombre(String nombre);

    List<Tecnico> findByApellido(String apellido);

    List<Tecnico> findByEspecialidad(String especialidad);
}
