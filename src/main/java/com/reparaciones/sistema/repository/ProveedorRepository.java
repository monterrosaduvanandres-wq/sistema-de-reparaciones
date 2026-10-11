
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByNombre(String nombre);

    Optional<Proveedor> findByCorreo(String correo);
}
