
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Repuesto;
import com.reparaciones.sistema.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

    List<Repuesto> findByNombre(String nombre);

    List<Repuesto> findByProveedor(Proveedor proveedor);

    List<Repuesto> findByStock(Integer stock);
}
