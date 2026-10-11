
package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CelularRepository extends JpaRepository<Celular, Long> {

    List<Celular> findByMarca(String marca);

    List<Celular> findByModelo(String modelo);

    List<Celular> findByColor(String color);

    List<Celular> findByCliente(Cliente cliente);
}
